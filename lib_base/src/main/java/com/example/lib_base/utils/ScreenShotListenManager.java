package com.example.lib_base.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.lib_base.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

public class ScreenShotListenManager {
    private static final String TAG = "ScreenShotListenManager";

    /**
     * 读取媒体数据库时需要读取的列
     */
    private static final String[] MEDIA_PROJECTIONS = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
    };
    /**
     * 读取媒体数据库时需要读取的列, 其中 WIDTH 和 HEIGHT 字段在 API 16 以后才有
     */
    private static final String[] MEDIA_PROJECTIONS_API_16 = {
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.WIDTH,
            MediaStore.Images.ImageColumns.HEIGHT,
    };

    /**
     * 截屏依据中的路径判断关键字
     */
    private static final String[] KEYWORDS = {
            "screenshot", "screen_shot", "screen-shot", "screen shot",
            "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap", "Screenshot", "截屏"
    };

    private static Point sScreenRealSize;

    /**
     * 已回调过的路径
     */
    private final static List<String> sHasCallbackPaths = new ArrayList<String>();

    private Activity mContext;

    private OnScreenShotListener mListener;

    private long mStartListenTime;

    /**
     * 内部存储器内容观察者
     */
    private MediaContentObserver mInternalObserver;

    /**
     * 外部存储器内容观察者
     */
    private MediaContentObserver mExternalObserver;

    /**
     * 运行在 UI 线程的 Handler, 用于运行监听器回调
     */
    private final Handler mUiHandler = new Handler(Looper.getMainLooper());

    private ScreenShotListenManager(Activity context) {
        if (context == null) {
            throw new IllegalArgumentException("The context must not be null.");
        }
        mContext = context;

        // 获取屏幕真实的分辨率
        if (sScreenRealSize == null) {
            sScreenRealSize = getRealScreenSize();
        }
    }

    public static ScreenShotListenManager newInstance(Activity context) {
        assertInMainThread();
        return new ScreenShotListenManager(context);
    }

    /**
     * 启动监听
     */
    public void startListen() {
        assertInMainThread();

//        sHasCallbackPaths.clear();

        // 记录开始监听的时间戳
        mStartListenTime = System.currentTimeMillis();

        // 创建内容观察者
        mInternalObserver = new MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, mUiHandler);
        mExternalObserver = new MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mUiHandler);

        // 注册内容观察者
        mContext.getContentResolver().registerContentObserver(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                true,
                mInternalObserver
        );
        mContext.getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                mExternalObserver
        );
    }

    /**
     * 停止监听
     */
    public void stopListen() {
        assertInMainThread();

        // 注销内容观察者
        if (mInternalObserver != null) {
            try {
                mContext.getContentResolver().unregisterContentObserver(mInternalObserver);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            mInternalObserver = null;
        }
        if (mExternalObserver != null) {
            try {
                mContext.getContentResolver().unregisterContentObserver(mExternalObserver);
            } catch (Exception e) {
                //e.printStackTrace();
            }
            mExternalObserver = null;
        }

        // 清空数据
        mStartListenTime = 0;
//        sHasCallbackPaths.clear();

        //切记！！！:必须设置为空 可能mListener 会隐式持有Activity导致释放不掉
        mListener = null;
    }

    /**
     * 处理媒体数据库的内容改变
     */
    private void handleMediaContentChange(Uri contentUri) {
        Cursor cursor = null;
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Bundle bundle = new Bundle();
                // 按照文件时间
                bundle.putStringArray(ContentResolver.QUERY_ARG_SORT_COLUMNS, new String[]{MediaStore.Images.ImageColumns.DATE_TAKEN});
                // 降序
                bundle.putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_DESCENDING);
                // 取第一张
                bundle.putInt(ContentResolver.QUERY_ARG_LIMIT, 1);
                cursor = mContext.getContentResolver().query(contentUri, MEDIA_PROJECTIONS_API_16,
                        bundle, null);

            } else {// 数据改变时查询数据库中最后加入的一条数据
                cursor = mContext.getContentResolver().query(
                        contentUri,
                        Build.VERSION.SDK_INT < 16 ? MEDIA_PROJECTIONS : MEDIA_PROJECTIONS_API_16,
                        null,
                        null,
                        MediaStore.Images.ImageColumns.DATE_TAKEN + " desc limit 1"
                );
            }

            if (cursor == null) {
                return;
            }
            if (!cursor.moveToFirst()) {
                return;
            }

            // 获取各列的索引
            int dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int dateTakenIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN);
            int widthIndex = -1;
            int heightIndex = -1;
            if (Build.VERSION.SDK_INT >= 16) {
                widthIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH);
                heightIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT);
            }

            // 获取行数据
            String data = cursor.getString(dataIndex);
            long dateTaken = cursor.getLong(dateTakenIndex);
            int width = 0;
            int height = 0;
            if (widthIndex >= 0 && heightIndex >= 0) {
                width = cursor.getInt(widthIndex);
                height = cursor.getInt(heightIndex);
            } else {
                // API 16 之前, 宽高要手动获取
                Point size = getImageSize(data);
                width = size.x;
                height = size.y;
            }

            // 处理获取到的第一行数据
            handleMediaRowData(data, dateTaken, width, height);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }

    private Point getImageSize(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        return new Point(options.outWidth, options.outHeight);
    }

    /**
     * 处理获取到的一行数据
     */
    private void handleMediaRowData(String data, long dateTaken, int width, int height) {
        if (checkScreenShot(data, dateTaken, width, height)) {
            if (mListener != null && !checkCallback(data)) {
                //延迟执行，
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        /**
                         *要执行的操作
                         */
                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mListener != null) {
                                    mListener.onShot(data);
                                }
                            }
                        });
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 800);
            }
        }
    }

    /**
     * 判断指定的数据行是否符合截屏条件
     */
    private boolean checkScreenShot(String data, long dateTaken, int width, int height) {
        /*
         * 判断依据一: 时间判断
         */
        // 如果加入数据库的时间在开始监听之前, 或者与当前时间相差大于10秒, 则认为当前没有截屏
        if (dateTaken < mStartListenTime || (System.currentTimeMillis() - dateTaken) > 10 * 1000) {
            return false;
        }
        // 这里只判断width 长截屏无法判断
        if (sScreenRealSize != null && (width > sScreenRealSize.x + 1 || width < sScreenRealSize.x - 1)) {
            return false;
        }

        /*
         * 判断依据三: 路径判断
         */
        if (TextUtils.isEmpty(data)) {
            return false;
        }
        data = data.toLowerCase();
        // 判断图片路径是否含有指定的关键字之一, 如果有, 则认为当前截屏了
        for (String keyWork : KEYWORDS) {
            if (data.contains(keyWork)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否已回调过, 某些手机ROM截屏一次会发出多次内容改变的通知; <br/>
     * 删除一个图片也会发通知, 同时防止删除图片时误将上一张符合截屏规则的图片当做是当前截屏.
     */
    private boolean checkCallback(String imagePath) {
        if (sHasCallbackPaths.contains(imagePath)) {
            return true;
        }
        // 大概缓存15~20条记录便可
        if (sHasCallbackPaths.size() >= 20) {
            for (int i = 0; i < 5; i++) {
                sHasCallbackPaths.remove(0);
            }
        }
        sHasCallbackPaths.add(imagePath);
        return false;
    }

    /**
     * 获取屏幕分辨率
     */
    private Point getRealScreenSize() {
        Point screenSize = null;
        try {
            screenSize = new Point();
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display defaultDisplay = windowManager.getDefaultDisplay();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                defaultDisplay.getRealSize(screenSize);
            } else {
                try {
                    Method mGetRawW = Display.class.getMethod("getRawWidth");
                    Method mGetRawH = Display.class.getMethod("getRawHeight");
                    screenSize.set(
                            (Integer) mGetRawW.invoke(defaultDisplay),
                            (Integer) mGetRawH.invoke(defaultDisplay)
                    );
                } catch (Exception e) {
                    screenSize.set(defaultDisplay.getWidth(), defaultDisplay.getHeight());
                    //e.printStackTrace();
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return screenSize;
    }

    public Bitmap createScreenShotBitmap(Context context, String screenFilePath) {

        View v = LayoutInflater.from(context).inflate(R.layout.base_test, null);
        ImageView iv = (ImageView) v.findViewById(R.id.iv);
        Bitmap bitmap = BitmapFactory.decodeFile(screenFilePath);
        iv.setImageBitmap(bitmap);

        //整体布局
        Point point = getRealScreenSize();
        v.measure(View.MeasureSpec.makeMeasureSpec(point.x, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(point.y, View.MeasureSpec.EXACTLY));

        v.layout(0, 0, point.x, point.y);

//        Bitmap result = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Bitmap result = Bitmap.createBitmap(v.getWidth(), v.getHeight() + dp2px(context, 140), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(result);
        c.drawColor(Color.WHITE);
        // Draw view to canvas
        v.draw(c);

        return result;
    }

    private int dp2px(Context ctx, float dp) {
        float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 设置截屏监听器
     */
    public void setListener(OnScreenShotListener listener) {
        mListener = listener;
    }

    public interface OnScreenShotListener {
        void onShot(String imagePath);
    }

    private static void assertInMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            String methodMsg = null;
            if (elements != null && elements.length >= 4) {
                methodMsg = elements[3].toString();
            }
            throw new IllegalStateException("Call the method must be in main thread: " + methodMsg);
        }
    }

    /**
     * 媒体内容观察者(观察媒体数据库的改变)
     */
    private class MediaContentObserver extends ContentObserver {

        private Uri mContentUri;

        public MediaContentObserver(Uri contentUri, Handler handler) {
            super(handler);
            mContentUri = contentUri;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            //super.onChange(selfChange, uri);
            handleMediaContentChange(mContentUri);
        }
    }

    public final Bitmap concatBitmap(@NotNull Context context, @Nullable String filePath, @Nullable Bitmap secondBitmap) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        if (secondBitmap == null) {
            return null;
        } else {
            int navHeight = getHeightWithNav(context) - getHeightWithoutNav(context);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            BitmapFactory.decodeFile(filePath, options);
            int width = options.outWidth;
            int height = options.outHeight - navHeight;
            int max = 1024 * 1024;

            int sampleSize;
            for (sampleSize = 1; width / sampleSize * height / sampleSize > max; sampleSize *= 2) {
            }

            //options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            Bitmap srcBmp = BitmapFactory.decodeFile(filePath, options);
            //先计算bitmap的宽高，因为bitmap的宽度和屏幕宽度是不一样的，需要按比例拉伸
            double var10000 = 1.0 * (double) secondBitmap.getWidth();

            //图片都拿到了，不需要检查
//            Intrinsics.checkExpressionValueIsNotNull(srcBmp, "srcBmp");

            double ratio = var10000 / (double) srcBmp.getWidth();
            int additionalHeight = (int) ((double) secondBitmap.getHeight() / ratio);

            //底部高能二维码图片
            Bitmap scaledBmp = Bitmap.createScaledBitmap(secondBitmap, srcBmp.getWidth(), additionalHeight, false);
            //到这里图片拉伸完毕
            //这里开始拼接，画到Canvas上
            Bitmap result = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight() - navHeight / sampleSize + additionalHeight, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas();
            canvas.setBitmap(result);
            //这里需要做个判断，因为一些系统是有导航栏的，所以截图时有导航栏，这里需要把导航栏遮住
            //计算出导航栏高度，然后draw时往上偏移一段距离
            Integer var10001 = getDisplayWidth((Activity) context);
            if (var10001 == null) {
                Intrinsics.throwNpe();
            }
            double navRatio = 1.0 * (double) var10001 / (double) srcBmp.getWidth();
            canvas.drawBitmap(srcBmp, 0.0F, 0, (Paint) null);
            canvas.drawBitmap(scaledBmp, 0.0F, srcBmp.getHeight(), (Paint) null);
            secondBitmap.recycle();
            return result;
        }
    }

    public final int getHeightWithNav(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Object var10000 = context.getSystemService(Context.WINDOW_SERVICE);
        if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
        } else {
            WindowManager windowManager = (WindowManager) var10000;
            Display d = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            d.getMetrics(displayMetrics);
            return displayMetrics.heightPixels;
        }
    }

    @Nullable
    public final int[] defaultDisplay(@NotNull Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        int[] pixels = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager var10000 = activity.getWindowManager();
        Intrinsics.checkExpressionValueIsNotNull(var10000, "activity.windowManager");
        var10000.getDefaultDisplay().getMetrics(dm);
        pixels[0] = dm.widthPixels;
        pixels[1] = dm.heightPixels;
        return pixels;
    }

    @Nullable
    public final Integer getDisplayWidth(@NotNull Activity activity) {
        Intrinsics.checkParameterIsNotNull(activity, "activity");
        int[] var10000 = this.defaultDisplay(activity);
        return var10000 != null ? var10000[0] : null;
    }

    /**
     * 获取屏幕高度，不包括navigation
     */
    public final int getHeightWithoutNav(@NotNull Context context) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Object var10000 = context.getSystemService(Context.WINDOW_SERVICE);
        if (var10000 == null) {
            throw new TypeCastException("null cannot be cast to non-null type android.view.WindowManager");
        } else {
            WindowManager windowManager = (WindowManager) var10000;
            Display d = windowManager.getDefaultDisplay();
            DisplayMetrics realDisplayMetrics = new DisplayMetrics();
            if (Build.VERSION.SDK_INT >= 17) {
                d.getRealMetrics(realDisplayMetrics);
            } else {
                try {
                    Method var9 = d.getClass().getDeclaredMethod("getRealMetrics");
                    Intrinsics.checkExpressionValueIsNotNull(var9, "d.javaClass.getDeclaredMethod(\"getRealMetrics\")");
                    Method method = var9;
                    method.setAccessible(true);
                    method.invoke(d, realDisplayMetrics);
                } catch (NoSuchMethodException | InvocationTargetException |
                         IllegalAccessException ignored) {
                }
            }
            return realDisplayMetrics.heightPixels;
        }
    }
}



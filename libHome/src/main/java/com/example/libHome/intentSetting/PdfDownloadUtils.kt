package com.example.libHome.intentSetting

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.text.TextUtils
import android.webkit.URLUtil
import com.blankj.utilcode.util.FileUtils
import com.example.lib_base.ext.toast
import java.io.File
import java.util.LinkedList

object PdfDownloadUtils {
    private val mDownloadMap: HashMap<String, Long> = hashMapOf()

    fun startDownLoad(context: Context, fileUrl: String, fileType: String) {
        val fileName = URLUtil.guessFileName(fileUrl, "高能智投", fileType)
        val downLoadManager: DownloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        // 查询是否在下载中
        getDownloadState(context, fileName, downLoadManager) { return }
        if (FileUtils.isFileExists(
                File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
            )
        ) {
            "文件已存在".toast()
            return
        }
        // 下载文件
        val request = DownloadManager.Request(Uri.parse(fileUrl))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(false)
            .setDescription("高能智投")
            .setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        val downloadId = downLoadManager.enqueue(request)
        mDownloadMap[fileName] = downloadId
        "下载开始".toast()
    }

    // 查询是否在下载中
    private inline fun getDownloadState(
        context: Context,
        fileName: String,
        downLoadManager: DownloadManager,
        downloadIng: () -> Unit
    ) {
        kotlin.runCatching {
            val query = DownloadManager.Query()
            val downloadId = mDownloadMap[fileName]
            if (downloadId != null) {
                query.setFilterById(downloadId)
                val cursor = downLoadManager.query(query)
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                when (cursor.getInt(columnIndex)) {
                    DownloadManager.STATUS_RUNNING -> {
                        "下载中".toast()
                        downloadIng()
                    }

                    DownloadManager.STATUS_FAILED -> {
                        mDownloadMap.remove(fileName)
                        FileUtils.delete(
                            File(
                                context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                                fileName
                            )
                        )
                    }

                    DownloadManager.STATUS_SUCCESSFUL -> {
                        mDownloadMap.remove(fileName)
                    }
                }
            }
        }.onFailure {
            mDownloadMap.remove(fileName)
            FileUtils.delete(
                File(
                    context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),
                    fileName
                )
            )
        }
    }
}
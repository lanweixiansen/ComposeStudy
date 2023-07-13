package com.example.libHome.calendar

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.provider.CalendarContract
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.lib_base.ext.toast
import java.util.TimeZone

object CalendarReminderUtils {
    private const val CALENDARS_NAME = "calendar_test"
    private const val CALENDARS_ACCOUNT_NAME = "test"
    private const val CALENDARS_ACCOUNT_TYPE = "test"
    private val mEventIdMap = LinkedHashMap<String, Long>()

    /**
     * 这里创建账户的展示名称，系统日历为我们提供了创建账户的入口，那我们就不使用系统自带的账户，创建一个自己app的账户
     */
    private const val CALENDARS_DISPLAY_NAME = "日历测试"


    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
     * 获取账户成功返回账户id，否则返回-1
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun checkAndAddCalendarAccount(context: Context): Int {
        val oldId = checkCalendarAccount(context)
        return if (oldId >= 0) {
            oldId
        } else {
            val addId = addCalendarAccount(context)
            if (addId >= 0) {
                checkCalendarAccount(context)
            } else {
                -1
            }
        }
    }

    /**
     * 检查是否存在现有账户，存在则返回账户id，否则返回-1
     */
    @SuppressLint("Range")
    private fun checkCalendarAccount(context: Context): Int {
        val userCursor = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        return userCursor.use { userCursor ->
            if (userCursor == null) { //查询返回空值
                return -1
            }
            val count = userCursor.count
            if (count > 0) { //存在现有账户，取第一个账户的id返回
                for (i in 0 until count) {
                    if (i == 0) {
                        userCursor.moveToFirst()
                    } else {
                        userCursor.moveToNext()
                    }
                    val type =
                        userCursor.getString(userCursor.getColumnIndex(CalendarContract.Calendars.ACCOUNT_TYPE))
                    if (type == CALENDARS_ACCOUNT_TYPE) {
                        return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID))
                    }
                }
            }
            -1
        }
    }

    /**
     * 添加日历账户，账户创建成功则返回账户id，否则返回-1
     */
    private fun addCalendarAccount(context: Context): Long {
        val timeZone = TimeZone.getDefault()
        val value = ContentValues()
        value.put(
            CalendarContract.Calendars.NAME,
            CALENDARS_NAME
        )
        value.put(
            CalendarContract.Calendars.ACCOUNT_NAME,
            CALENDARS_ACCOUNT_NAME
        )
        value.put(
            CalendarContract.Calendars.ACCOUNT_TYPE,
            CALENDARS_ACCOUNT_TYPE
        )
        value.put(
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CALENDARS_DISPLAY_NAME
        )
        //        可见度
        value.put(CalendarContract.Calendars.VISIBLE, 1)
        //        日历颜色
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
        //        权限
        value.put(
            CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
            CalendarContract.Calendars.CAL_ACCESS_OWNER
        )
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1)
        //        时区
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.id)
        value.put(
            CalendarContract.Calendars.OWNER_ACCOUNT,
            CALENDARS_ACCOUNT_NAME
        )
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)
        var calendarUri = CalendarContract.Calendars.CONTENT_URI
        calendarUri = calendarUri.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(
                CalendarContract.Calendars.ACCOUNT_NAME,
                CALENDARS_ACCOUNT_NAME
            )
            .appendQueryParameter(
                CalendarContract.Calendars.ACCOUNT_TYPE,
                CALENDARS_ACCOUNT_TYPE
            )
            .build()
        val result = context.contentResolver.insert(calendarUri, value)
        return if (result == null) -1 else ContentUris.parseId(result)
    }

    /**
     * 这个是关键方法，调用插入日程提醒
     *
     * @param context
     * @param eventId         事件id
     * @param title           提醒事件标题
     * @param description     事件描述
     * @param reminderTime    任务开始时间
     * @param endTime         任务结束时间
     * @param previousMinutes 提前多少分钟提醒
     * @param isAlarm         是否需要闹钟提醒
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("Range")
    fun addCalendarEvent(
        context: Context?,
        id: String,
        title: String?,
        description: String?,
        reminderTime: Long,
        endTime: Long,
        previousMinutes: Int,
        isAlarm: Boolean
    ): Long {
        if (context == null) {
            return -1L
        }
        val calId = checkAndAddCalendarAccount(context) //获取日历账户的id
        if (calId < 0) { //获取账户id失败直接返回，添加日历事件失败
            return -1L
        }
        val eventId = createdEventId(id)
        //添加日历事件
        val event = ContentValues()
        event.put("title", title)
        event.put("description", description)
        event.put("calendar_id", calId) //插入账户的id
        event.put("eventStatus", 1)
        event.put(CalendarContract.Events._ID, eventId)
        event.put(CalendarContract.Events.HAS_EXTENDED_PROPERTIES, true)
        event.put(CalendarContract.Events.DTSTART, reminderTime)
        event.put(CalendarContract.Events.DTEND, endTime)
        if (isAlarm) {
            event.put(CalendarContract.Events.HAS_ALARM, 1) //设置有闹钟提醒，但是经测试，此方案无效
        }
        //这个是时区，必须有
        event.put(
            CalendarContract.Events.EVENT_TIMEZONE,
            TimeZone.getDefault().displayName
        )
        //添加日历事件失败直接返回
        val newEvent =
            context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, event)
                ?: return -1L
        //扩展属性 用于高版本安卓系统设置闹钟提醒
        if (isAlarm) {
            var extendedPropUri = CalendarContract.ExtendedProperties.CONTENT_URI
            extendedPropUri = extendedPropUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(
                    CalendarContract.Calendars.ACCOUNT_NAME,
                    CALENDARS_ACCOUNT_NAME
                )
                .appendQueryParameter(
                    CalendarContract.Calendars.ACCOUNT_TYPE,
                    CALENDARS_ACCOUNT_TYPE
                ).build()
            val extendedProperties = ContentValues()
            extendedProperties.put(
                CalendarContract.ExtendedProperties.EVENT_ID,
                ContentUris.parseId(newEvent)
            )
            extendedProperties.put(
                CalendarContract.ExtendedProperties.VALUE,
                "{\"need_alarm\":true}"
            )
            extendedProperties.put(CalendarContract.ExtendedProperties.NAME, "agenda_info")
            val uriExtended =
                context.contentResolver.insert(extendedPropUri, extendedProperties)
                    ?: //添加事件提醒失败直接返回
                    return -1L
        }
        //事件提醒的设定
        val values = ContentValues()
        values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent))
        values.put(CalendarContract.Reminders.MINUTES, previousMinutes) // 提前previousDate天有提醒
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        val uri = context.contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, values)
            ?: //添加事件提醒失败直接返回
            return -1L
        Toast.makeText(context, "设置日程成功!!!", Toast.LENGTH_LONG).show()
        return eventId
    }

    private fun createdEventId(id: String): Long {
        if (mEventIdMap.containsKey(id)) {
            return mEventIdMap.getOrDefault(id, 0L)
        }
        var eventId = (Math.random() * 1000 + 1).toLong()
        while (mEventIdMap.containsValue(eventId)) {
            eventId = (Math.random() * 1000 + 1).toLong()
        }
        mEventIdMap[id] = eventId
        return eventId
    }

    /**
     * 删除日历事件
     */
    fun deleteCalendarEvent(context: Context?, id: String?) {
        if (context == null) {
            return
        }
        if (!mEventIdMap.containsKey(id)) {
            return
        }
        val deleteUri =
            ContentUris.withAppendedId(
                CalendarContract.Events.CONTENT_URI,
                mEventIdMap.getOrDefault(id, -1L)
            )
        val rows = context.contentResolver.delete(deleteUri, null, null)
        if (rows == -1) { //事件删除失败
            return
        }
        "删除日程成功".toast()
        mEventIdMap.remove(id)
    }


}
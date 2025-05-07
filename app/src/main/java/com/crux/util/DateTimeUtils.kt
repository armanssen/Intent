package com.crux.util

import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object DateTimeUtils {

    const val FORMAT_FULL_DATE = "EEE, MMM d, yyyy"
    const val FORMAT_FULL_TIME_24 = "HH:mm"

    // we set 23:59:59 time to 'all day' tasks
    const val ALL_DAY_HOUR = 23
    const val ALL_DAY_MINUTE = 59
    const val ALL_DAY_SECOND = 59

    fun formatDate(
        millis: Long,
        formatPattern: String = FORMAT_FULL_DATE
    ): String {
        return Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .format(
                DateTimeFormatter.ofPattern(formatPattern)
            )
    }

    fun getDateHour(millis: Long): Int {
        return Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .hour
    }

    fun getDateMinute(millis: Long): Int {
        return Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .minute
    }

    fun getDateWithTime(
        millis: Long,
        hour: Int,
        minute: Int,
        second: Int = 0
    ): Long {
        return Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .withHour(hour)
            .withMinute(minute)
            .withSecond(second)
            .withNano(0)
            .withZoneSameInstant(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
    }

    fun isAllDay(millis: Long): Boolean {
        val zonedDateTime = Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())

        return zonedDateTime.hour == ALL_DAY_HOUR
                && zonedDateTime.minute == ALL_DAY_MINUTE
                && zonedDateTime.second == ALL_DAY_SECOND
    }
}

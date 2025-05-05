package com.crux.screens.add_or_edit_task.ui

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatDate(millis: Long): String {
    val zonedDateTime = Instant
        .ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy")

    return zonedDateTime.format(formatter)
}

fun formatDueTime(millis: Long): String {
    val zonedDateTime = Instant
        .ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    return zonedDateTime.format(formatter)

    // TODO do when setting 12 vs 24 hour format
//    return zonedDateTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
}

fun isAllDay(dueDateTimeMillis: Long): Boolean {
    val localDateTime = Instant.ofEpochMilli(dueDateTimeMillis)
        .atZone(ZoneId.systemDefault())
    return localDateTime.hour == 23 && localDateTime.minute == 59 && localDateTime.second == 59
}

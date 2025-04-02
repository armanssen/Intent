package com.crux.screens.add_or_edit_task.ui

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun formatDate(millis: Long): String {
    val zonedDateTime = Instant.ofEpochMilli(millis)
        .atZone(ZoneId.systemDefault()) // Convert to local time

    val formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy")
    return zonedDateTime.format(formatter)
}

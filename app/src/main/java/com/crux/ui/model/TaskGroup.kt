package com.crux.ui.model

import androidx.annotation.StringRes
import com.crux.R
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


sealed class TaskGroup(
    @StringRes val labelResId: Int
) {
    object Overdue : TaskGroup(R.string.task_group_overdue)
    object Today : TaskGroup(R.string.task_group_today)
    object Tomorrow : TaskGroup(R.string.task_group_tomorrow)
    object NextWeek : TaskGroup(R.string.task_group_next_week)
    object ThisMonth : TaskGroup(R.string.task_group_this_month)
    object NextMonth : TaskGroup(R.string.task_group_next_month)
    object Later : TaskGroup(R.string.task_group_later)
    object SomeDay : TaskGroup(R.string.task_group_some_day)

    class WeekDay(
        val day: DayOfWeek
    ) : TaskGroup(R.string.task_group_weekday) // This will be dynamic
}

enum class TimeOfDay {
    MORNING, NOON, EVENING
}

fun groupTasksByDueDateTime(
    tasks: List<TaskUi>,
    startOfWeek: DayOfWeek = DayOfWeek.MONDAY // or DayOfWeek.MONDAY
): Map<TaskGroup, List<TaskUi>> {
    val now = LocalDateTime.now()
    val today = now.toLocalDate()
    val tomorrow = today.plusDays(1)

    // Calculate the start of the next week based on the provided startOfWeek
    val todayDow = today.dayOfWeek
    val daysUntilNextWeekStart = ((startOfWeek.value - todayDow.value + 7) % 7).let {
        if (it == 0) 7 else it // ensure we jump to *next* week's start, not today
    }
    val nextWeekStart = today.plusDays(daysUntilNextWeekStart.toLong())
    val nextWeekEnd = nextWeekStart.plusDays(6)

    val nextMonthStart = today.plusMonths(1).withDayOfMonth(1)
    val nextMonthEnd = nextMonthStart.plusMonths(1).minusDays(1)

    val grouped = tasks.groupBy { task ->
        val due = task.dueDateTime?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        } ?: return@groupBy TaskGroup.SomeDay

        val dueDate = due.toLocalDate()

        when {
            due.isBefore(now) -> {
                TaskGroup.Overdue
            }
            dueDate.isEqual(today) -> {
                TaskGroup.Today
            }
            dueDate.isEqual(tomorrow) -> {
                TaskGroup.Tomorrow
            }
            dueDate.isAfter(tomorrow) && dueDate.isBefore(nextWeekStart) -> {
                TaskGroup.WeekDay(dueDate.dayOfWeek)
            }
            !dueDate.isBefore(nextWeekStart) && !dueDate.isAfter(nextWeekEnd) -> {
                TaskGroup.NextWeek
            }
            dueDate.isAfter(nextWeekEnd) && dueDate.isBefore(nextMonthStart) -> {
                TaskGroup.ThisMonth
            }
            !dueDate.isBefore(nextMonthStart) && !dueDate.isAfter(nextMonthEnd) -> {
                TaskGroup.NextMonth
            }
            else -> {
                TaskGroup.Later
            }
        }
    }

    // Filter out completed tasks in the Overdue group
    return grouped
        .mapValues { (group, list) ->
            if (group == TaskGroup.Overdue) {
                list.filterNot { it.isCompleted }
            } else {
                list
            }
        }
        .filterValues {
            it.isNotEmpty()
        }
}

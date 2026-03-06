package com.intent.core.ui.worker

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.intent.R
import kotlin.random.Random

class TaskReminderWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override suspend fun doWork(): Result {
        val taskTitle = inputData.getString("title") ?: return Result.failure()

        val notification = NotificationCompat.Builder(applicationContext, "todo_channel")
            .setContentTitle("Task Due")
            .setContentText(taskTitle)
            .build()

        NotificationManagerCompat.from(applicationContext)
            .notify(Random.nextInt(), notification)

        return Result.success()
    }
}
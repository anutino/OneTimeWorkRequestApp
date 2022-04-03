package com.example.onetimeworkrequestapp

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class DownloadPictureWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context, workerParams) {

    override fun doWork(): Result {
        Thread.sleep(2000)
        val int = R.drawable.cvetovaya
        return Result.success(workDataOf("IMAGE_ID" to int))
    }

}
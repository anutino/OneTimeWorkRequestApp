package com.example.onetimeworkrequestapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator

class MainActivity : AppCompatActivity() {

    private lateinit var mImageButton: ImageButton
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mAnimator: ObjectAnimator
    private val mWorkManager by lazy {
        WorkManager.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val downloadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<DownloadPictureWorker>()
            .build()
        mImageButton = findViewById(R.id.download_button)
        mProgressBar = findViewById(R.id.progressbar)
        mProgressBar.incrementProgressBy(1)

        mImageButton.setOnClickListener {
            mImageButton.visibility = View.GONE
            mProgressBar.visibility = View.VISIBLE

            mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 0, 100)
            mAnimator.interpolator = DecelerateInterpolator(2f);
            mAnimator.duration = 2000
            mAnimator.start()

            mWorkManager.enqueue(downloadWorkRequest)
        }

        mWorkManager.getWorkInfoByIdLiveData(downloadWorkRequest.id).observe(this, { info ->
            if (info != null && info.state.isFinished) {
                mImageButton.setImageResource(info.outputData.getInt("IMAGE_ID", R.drawable.dwnld))
                mImageButton.visibility = View.VISIBLE
                mProgressBar.visibility = View.GONE
            }
        })
    }
}

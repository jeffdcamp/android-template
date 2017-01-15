package org.jdc.template.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest
import timber.log.Timber

class SampleJob : Job() {

    override fun onRunJob(params: Job.Params): Job.Result {
        Timber.e("onRunJob: **************************** RUNNING!!! *******************************************  [" + Thread.currentThread().name + "]")
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            Timber.e("Sleep Failure")
        }

        // run your job
        return Job.Result.SUCCESS
    }

    companion object {
        val TAG = "SampleJob"

        fun schedule() {
            JobRequest.Builder(TAG)
                    .setRequirementsEnforced(true)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setExecutionWindow(1000L, 2000L)
                    .setUpdateCurrent(true)
                    .build()
                    .schedule()
        }
    }

}

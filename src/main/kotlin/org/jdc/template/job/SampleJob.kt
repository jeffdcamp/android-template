package org.jdc.template.job

import android.util.Log
import com.evernote.android.job.Job
import com.evernote.android.job.JobRequest

class SampleJob : Job() {

    override fun onRunJob(params: Job.Params): Job.Result {
        Log.e(TAG, "onRunJob: **************************** RUNNING!!! *******************************************  [" + Thread.currentThread().name + "]")
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        // run your job
        return Job.Result.SUCCESS
    }

    companion object {
        val TAG = "SampleJob"

        fun schedule() {
            JobRequest.Builder(SampleJob.TAG)
                    .setRequirementsEnforced(true)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setExecutionWindow(1000L, 2000L)
                    .setUpdateCurrent(true)
                    .build()
                    .schedule()
        }
    }

}

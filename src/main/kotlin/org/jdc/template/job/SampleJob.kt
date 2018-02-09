package org.jdc.template.job

import android.support.annotation.WorkerThread
import com.evernote.android.job.Job
import timber.log.Timber
import javax.inject.Inject

class SampleJob
@Inject constructor(

) : Job() {

    @WorkerThread
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
        const val TAG = "SampleJob"
    }

}

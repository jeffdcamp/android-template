package org.jdc.template.job


import com.evernote.android.job.JobRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppJobScheduler
@Inject constructor() {
    fun scheduleSampleJob() {
        JobRequest.Builder(SampleJob.TAG)
                .setRequirementsEnforced(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setExecutionWindow(1000L, 2000L)
                .setUpdateCurrent(true)
                .build()
                .scheduleAsync()
    }
}

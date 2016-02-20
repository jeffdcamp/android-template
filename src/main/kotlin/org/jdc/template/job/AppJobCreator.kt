package org.jdc.template.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator

class AppJobCreator : JobCreator {

    override fun create(tag: String): Job? {
        when (tag) {
            SampleJob.TAG -> return SampleJob()
            else -> return null
        }
    }
}

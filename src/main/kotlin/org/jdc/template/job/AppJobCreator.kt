package org.jdc.template.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AppJobCreator @Inject
constructor(
        private val syncJobProvider: Provider<SampleJob>
) : JobCreator {

    override fun create(tag: String): Job? {
        return when (tag) {
            SampleJob.TAG -> syncJobProvider.get()
            else -> {
                Timber.w("Cannot find job for tag [$tag]. Be sure to add creation to AppJobCreator")
                return null
            }
        }
    }
}

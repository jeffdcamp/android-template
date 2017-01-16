package org.jdc.template.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;

import javax.annotation.Nonnull;

import timber.log.Timber;

public class SampleJob extends Job {
    public static final String TAG = "SampleJob";

    @Override
    @Nonnull
    protected Result onRunJob(Params params) {
        Timber.i("onRunJob: **************************** RUNNING!!! *******************************************  [%s]", Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Timber.e("Sleep Failure");
        }

        // run your job
        return Result.SUCCESS;
    }

    public static void schedule() {
        new JobRequest.Builder(SampleJob.TAG)
                .setRequirementsEnforced(true)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                .setExecutionWindow(1_000L, 2_000L)
                .setUpdateCurrent(true)
                .build()
                .schedule();
    }

}

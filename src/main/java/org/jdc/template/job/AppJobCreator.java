package org.jdc.template.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

public class AppJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case SampleJob.TAG:
                return new SampleJob();
            default:
                return null;
        }
    }
}

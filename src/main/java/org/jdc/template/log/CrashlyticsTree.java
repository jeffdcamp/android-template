package org.jdc.template.log;

import android.util.Log;

import timber.log.Timber;

public class CrashlyticsTree extends Timber.Tree {

    @Override
    protected boolean isLoggable(String tag, int priority) {
        return priority == Log.ERROR;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.ERROR) {
            if (t != null) {
//                Crashlytics.log(1, "message", message);
//                Crashlytics.logException(t);
            } else {
//                Crashlytics.logException(new NonFatalCrashLogException(message));
            }
        }
    }

    public class NonFatalCrashLogException extends RuntimeException {
        public NonFatalCrashLogException(String message) {
            super(message);
        }
    }
}

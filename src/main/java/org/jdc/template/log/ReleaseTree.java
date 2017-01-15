package org.jdc.template.log;

import android.annotation.SuppressLint;
import android.util.Log;

import timber.log.Timber;

@SuppressLint("LogNotTimber")
public class ReleaseTree extends Timber.Tree {

    public static final int MAX_LOG_LENGTH = 4000;

    @Override
    protected boolean isLoggable(String tag, int priority) {
        switch (priority) {
            case Log.VERBOSE:
            case Log.DEBUG:
            case Log.INFO:
                return false;
            default:
                return true;
        }
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (message.length() < MAX_LOG_LENGTH) {
            if (priority == Log.ERROR && t != null) {
                // Crashlytics.log(t)
            }

            if (priority == Log.ASSERT) {
                Log.wtf(tag, message);
            } else {
                Log.println(priority, tag, message);
            }
            return;
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        for (int i = 0, length = message.length(); i < length; i++) {
            int newline = message.indexOf('\n', i);
            newline = newline != -1 ? newline : length;
            do {
                int end = Math.min(newline, i + MAX_LOG_LENGTH);
                String part = message.substring(i, end);
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part);
                } else {
                    Log.println(priority, tag, part);
                }
                i = end;
            } while (i < newline);
        }
    }
}

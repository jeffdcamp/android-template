package org.jdc.template.log;

import org.dbtools.android.domain.log.DBToolsLogger;

import timber.log.Timber;

public class DBToolsTimberLogger implements DBToolsLogger {
    @Override
    public void v(String tag, String message) {
        Timber.v(formatMessage(tag, message));
    }

    @Override
    public void v(String tag, String message, Throwable t) {
        Timber.v(t, formatMessage(tag, message));
    }

    @Override
    public void d(String tag, String message) {
        Timber.d(formatMessage(tag, message));
    }

    @Override
    public void d(String tag, String message, Throwable t) {
        Timber.d(t, formatMessage(tag, message));
    }

    @Override
    public void i(String tag, String message) {
        Timber.i(formatMessage(tag, message));
    }

    @Override
    public void i(String tag, String message, Throwable t) {
        Timber.i(t, formatMessage(tag, message));
    }

    @Override
    public void w(String tag, String message) {
        Timber.w(formatMessage(tag, message));
    }

    @Override
    public void w(String tag, String message, Throwable t) {
        Timber.w(t, formatMessage(tag, message));
    }

    @Override
    public void e(String tag, String message) {
        Timber.e(formatMessage(tag, message));
    }

    @Override
    public void e(String tag, String message, Throwable t) {
        Timber.e(t, formatMessage(tag, message));
    }

    @Override
    public void wtf(String tag, String message) {
        Timber.wtf(formatMessage(tag, message));
    }

    @Override
    public void wtf(String tag, String message, Throwable t) {
        Timber.wtf(t, formatMessage(tag, message));
    }

    private String formatMessage(String tag, String message) {
        if (tag != null && !tag.isEmpty()) {
            return "[" + tag + "] " + message;
        } else {
            return message;
        }
    }
}

package org.jdc.template.log;

import timber.log.Timber;

public class JavaTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        String logMessage;
        if (tag != null && !tag.isEmpty()) {
            logMessage = "[" + tag + "] " + message;
        } else {
            logMessage = message;
        }

        System.out.println(logMessage);
    }
}

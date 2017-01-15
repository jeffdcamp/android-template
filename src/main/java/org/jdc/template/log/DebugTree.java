package org.jdc.template.log;

import timber.log.Timber;

public class DebugTree extends Timber.DebugTree {

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        // add line number
        return super.createStackElementTag(element) + ":" + element.getLineNumber();
    }
}

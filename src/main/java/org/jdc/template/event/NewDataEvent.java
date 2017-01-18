package org.jdc.template.event;

public class NewDataEvent {
    private final boolean success;
    private Throwable throwable;

    public NewDataEvent(boolean success) {
        this.success = success;
    }

    public NewDataEvent(boolean success, Throwable throwable) {
        this.success = success;
        this.throwable = throwable;
    }

    public boolean isSuccess() {
        return success;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}

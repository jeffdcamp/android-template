package org.jdc.template.event;

public class EditIndividualEvent {
    private final long id;

    public EditIndividualEvent() {
        this.id = -1;
    }

    public EditIndividualEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

package org.jdc.template.event;

public class IndividualDeletedEvent {
    private final long id;

    public IndividualDeletedEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

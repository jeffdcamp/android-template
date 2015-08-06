package org.company.project.event;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

public class MyDBToolsEventBus implements org.dbtools.android.domain.DBToolsEventBus {
    private EventBus bus;

    @Inject
    public MyDBToolsEventBus(EventBus bus) {
        this.bus = bus;
    }

    public void post(Object event) {
        bus.post(event);
    }
}

package org.jdc.template.model.database.main.individual;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TestModule.class)
public interface TestComponent {
    void inject(IndividualManagerTest target);
}
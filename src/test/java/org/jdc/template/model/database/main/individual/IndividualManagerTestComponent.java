package org.jdc.template.model.database.main.individual;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = IndividualManagerTestModule.class)
public interface IndividualManagerTestComponent {
    void inject(IndividualManagerTest target);
}
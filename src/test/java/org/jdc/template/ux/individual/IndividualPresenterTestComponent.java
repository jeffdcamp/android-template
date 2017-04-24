package org.jdc.template.ux.individual;


import org.jdc.template.inject.CommonTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {IndividualPresenterTestModule.class, CommonTestModule.class})
public interface IndividualPresenterTestComponent {
    void inject(IndividualPresenterTest target);
}
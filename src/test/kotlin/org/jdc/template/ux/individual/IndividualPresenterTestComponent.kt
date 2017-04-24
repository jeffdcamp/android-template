package org.jdc.template.ux.individual

import dagger.Component
import org.jdc.template.inject.CommonTestModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(IndividualPresenterTestModule::class,
        CommonTestModule::class))
interface IndividualPresenterTestComponent {
    fun inject(target: IndividualPresenterTest)
}
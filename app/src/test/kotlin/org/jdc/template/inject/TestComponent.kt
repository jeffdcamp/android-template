package org.jdc.template.inject

import dagger.Component
import org.jdc.template.datasource.webservice.colors.ColorServiceTest
import javax.inject.Singleton

@Singleton
@Component(modules = [CommonTestModule::class])
interface TestComponent {
    fun inject(mediaWebServiceTest: ColorServiceTest)
}
package org.jdc.template.inject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import org.jdc.template.ui.fragment.SettingsFragment
import org.jdc.template.ux.about.AboutFragment
import org.jdc.template.ux.directory.DirectoryFragment
import org.jdc.template.ux.individual.IndividualFragment
import org.jdc.template.ux.individualedit.IndividualEditFragment
import kotlin.reflect.KClass

@Module
abstract class FragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(DirectoryFragment::class)
    abstract fun bindDirectoryFragment(fragment: DirectoryFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(IndividualFragment::class)
    abstract fun bindIndividualFragment(fragment: IndividualFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(IndividualEditFragment::class)
    abstract fun bindIndividualEditFragment(fragment: IndividualEditFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(AboutFragment::class)
    abstract fun aboutFragment(fragment: AboutFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SettingsFragment::class)
    abstract fun settingsFragment(fragment: SettingsFragment): Fragment

    @Binds
    abstract fun bindFragmentFactory(factory: InjectorFragmentFactory): FragmentFactory
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(value = AnnotationRetention.RUNTIME)
@MapKey
internal annotation class FragmentKey(val value: KClass<out Fragment>)
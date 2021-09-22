package org.jdc.template.ui.navigation

import android.content.Context
import androidx.navigation.NavGraph
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import org.dbtools.android.work.ux.monitor.WorkManagerStatusFragment
import org.jdc.template.ux.about.AboutFragment
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.acknowledgement.AcknowledgmentsFragment
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.ux.directory.DirectoryFragment
import org.jdc.template.ux.directory.DirectoryRoute
import org.jdc.template.ux.individual.IndividualFragment
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditFragment
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.main.TestActivity
import org.jdc.template.ux.main.TestRoute
import org.jdc.template.ux.settings.SettingsFragment
import org.jdc.template.ux.settings.SettingsRoute

object MainNav {
    fun createNavGraph(context: Context, navHostFragment: NavHostFragment): NavGraph {
        return navHostFragment.createGraph(startDestination = DirectoryRoute.route) {
            // App routes
            DirectoryRoute.addNavigationRoute<DirectoryFragment>(this, context)
            IndividualRoute.addNavigationRoute<IndividualFragment>(this, context)
            IndividualEditRoute.addNavigationRoute<IndividualEditFragment>(this, context)
            SettingsRoute.addNavigationRoute<SettingsFragment>(this, context)
            AboutRoute.addNavigationRoute<AboutFragment>(this, context)
            AcknowledgmentsRoute.addNavigationRoute<AcknowledgmentsFragment>(this, context)

            TestRoute.addNavigationRoute<TestActivity>(this)

            // Library routes
            WorkManagerStatusRoute.addNavigationRoute<WorkManagerStatusFragment>(this, context)
        }
    }
}
package org.jdc.template.ux.directory

import android.os.Bundle
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Int
import org.jdc.template.model.database.main.individual.Individual

class DirectoryContract {
    interface View {
        fun showNewIndividual()
        fun showIndividualList(list: List<Individual>)
        fun showIndividual(individualId: Long)
        fun scrollToPosition(scrollPosition: Int)
        fun getListScrollPosition(): Int
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, DirectoryActivity::class)

    object IntentOptions

    object SaveStateOptions {
        var Bundle.scrollPosition by BundleExtra.Int()
    }
}

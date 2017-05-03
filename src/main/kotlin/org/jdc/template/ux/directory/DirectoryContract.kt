package org.jdc.template.ux.directory

import me.eugeniomarletti.extras.ActivityCompanion
import org.jdc.template.model.database.main.individual.Individual

class DirectoryContract {
    interface View {
        fun showNewIndividual()
        fun showIndividualList(list: List<Individual>)
        fun showIndividual(individualId: Long)
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, DirectoryActivity::class)

    object IntentOptions

    object SaveStateOptions
}

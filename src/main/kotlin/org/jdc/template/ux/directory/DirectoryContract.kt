package org.jdc.template.ux.directory

import org.jdc.template.model.database.main.individual.Individual

class DirectoryContract {
    interface View {
        fun showNewIndividual()
        fun showIndividualList(list: List<Individual>)
        fun showIndividual(individualId: Long)
    }

    object Extras {
    }
}

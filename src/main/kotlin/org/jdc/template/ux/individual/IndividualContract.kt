package org.jdc.template.ux.individual

import org.jdc.template.model.database.main.individual.Individual

class IndividualContract {
    interface View {
        fun showIndividual(individual: Individual)
        fun close()
        fun showEditIndividual(individualId: Long)
        fun promptDeleteIndividual()
    }

    object Extras {
        const val EXTRA_ID = "INDIVIDUAL_ID"
    }
}

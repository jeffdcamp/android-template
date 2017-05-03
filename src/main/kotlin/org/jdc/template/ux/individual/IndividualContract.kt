package org.jdc.template.ux.individual

import android.content.Intent
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.jdc.template.model.database.main.individual.Individual

class IndividualContract {
    interface View {
        fun showIndividual(individual: Individual)
        fun close()
        fun showEditIndividual(individualId: Long)
        fun promptDeleteIndividual()
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, IndividualActivity::class)

    object IntentOptions {
        var Intent.individualId by IntentExtra.Long(defaultValue = 0L)
    }
}

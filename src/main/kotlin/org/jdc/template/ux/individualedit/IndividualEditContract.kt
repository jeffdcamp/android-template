package org.jdc.template.ux.individualedit

import android.content.Intent
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.jdc.template.model.database.main.individual.Individual
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class IndividualEditContract {
    interface View {
        fun showIndividual(individual: Individual)
        fun validateIndividualData(): Boolean
        fun getIndividualDataFromUi(individual: Individual)
        fun close()
        fun showAlarmTimeSelector(time: LocalTime)
        fun showAlarmTime(time: LocalTime)
        fun showBirthDateSelector(date: LocalDate)
        fun showBirthDate(date: LocalDate)
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, IndividualEditActivity::class)

    object IntentOptions {
        var Intent.individualId by IntentExtra.Long(defaultValue = -1L)
    }
}

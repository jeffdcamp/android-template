package org.jdc.template.ux.individualedit

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

    object Extras {
        const val EXTRA_ID = "INDIVIDUAL_ID"
    }
}

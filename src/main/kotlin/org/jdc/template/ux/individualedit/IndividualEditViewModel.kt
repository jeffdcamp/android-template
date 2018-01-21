package org.jdc.template.ux.individualedit

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import kotlinx.coroutines.experimental.launch
import org.jdc.template.R
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.util.CoroutineContextProvider
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

class IndividualEditViewModel
@Inject constructor(
        private val cc: CoroutineContextProvider,
        private val individualDao: IndividualDao
) : ViewModel() {

    var individual = Individual()

    // Fields
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val phone = ObservableField<String>()
    val email = ObservableField<String>()
    val birthDate = ObservableField<LocalDate?>()
    val alarmTime = ObservableField<LocalTime>()

    // Events
    val onIndividualSavedEvent = SingleLiveEvent<Void>()
    val onValidationSaveErrorEvent = SingleLiveEvent<RequiredField>()
    val onShowBirthDateSelectionEvent = SingleLiveEvent<LocalDate>()
    val onShowAlarmTimeSelectionEvent = SingleLiveEvent<LocalTime>()

    fun loadIndividual(individualId: Long) = launch(cc.commonPool) {
        individualDao.findById(individualId)?.let {
            individual = it

            firstName.set(it.firstName)
            lastName.set(it.lastName)
            phone.set(it.phone)
            email.set(it.email)
            birthDate.set(it.birthDate)
            alarmTime.set(it.alarmTime)
        }
    }

    fun saveIndividual() = launch(cc.commonPool) {
        if (!validate()) {
            return@launch
        }

        individual.firstName = firstName.get() ?: return@launch
        individual.lastName = lastName.get() ?: ""
        individual.phone = phone.get() ?: ""
        individual.email = email.get() ?: ""
        individual.birthDate = birthDate.get()
        individual.alarmTime = alarmTime.get() ?: LocalTime.now()

        if (individual.id <= 0) {
            individualDao.insert(individual)
        } else {
            individualDao.update(individual)
        }

        onIndividualSavedEvent.postCall()
    }

    private fun validate(): Boolean {
        if (firstName.get().isNullOrBlank()) {
            onValidationSaveErrorEvent.postValue(RequiredField.FIRST_NAME)
            return false
        }

        return true
    }

    fun onBirthDateClicked() {
        onShowBirthDateSelectionEvent.postValue(birthDate.get() ?: LocalDate.now())
    }

    fun onAlarmTimeClicked() {
        onShowAlarmTimeSelectionEvent.postValue(alarmTime.get() ?: LocalTime.now())
    }

    enum class RequiredField(val errorMessageId: Int) {
        FIRST_NAME(R.string.required),
    }
}
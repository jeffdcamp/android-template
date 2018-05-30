package org.jdc.template.ux.individualedit

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import kotlinx.coroutines.experimental.launch
import org.jdc.template.R
import org.jdc.template.datasource.database.main.MainDatabase
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.livedata.SingleLiveEvent
import org.jdc.template.util.CoroutineContextProvider
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

class IndividualEditViewModel
@Inject constructor(
        private val application: Application,
        private val cc: CoroutineContextProvider,
        private val mainDatabase: MainDatabase
) : ViewModel() {

    var individual = Individual()

    // Fields
    val firstName = ObservableField<String>()
    val lastName = ObservableField<String>()
    val phone = ObservableField<String>()
    val email = ObservableField<String>()
    val birthDate = ObservableField<LocalDate?>()
    val alarmTime = ObservableField<LocalTime>()
    val profileUrl = ObservableField<String>()

    // Events
    val onIndividualSavedEvent = SingleLiveEvent<Void>()
    val onValidationSaveErrorEvent = SingleLiveEvent<FieldValidationError>()
    val onShowBirthDateSelectionEvent = SingleLiveEvent<LocalDate>()
    val onShowAlarmTimeSelectionEvent = SingleLiveEvent<LocalTime>()

    fun loadIndividual(individualId: Long) = launch(cc.commonPool) {
        mainDatabase.individualDao().findById(individualId)?.let {
            individual = it

            firstName.set(it.firstName)
            lastName.set(it.lastName)
            phone.set(it.phone)
            email.set(it.email)
            birthDate.set(it.birthDate)
            alarmTime.set(it.alarmTime)
            profileUrl.set(it.profileUrl)
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
        individual.profileUrl = profileUrl.get() ?: application.resources.getString(R.string.defaultProfileUrl)

        val individualDao = mainDatabase.individualDao()
        if (individual.id <= 0) {
            individualDao.insert(individual)
        } else {
            individualDao.update(individual)
        }

        onIndividualSavedEvent.postCall()
    }

    private fun validate(): Boolean {
        if (firstName.get().isNullOrBlank()) {
            onValidationSaveErrorEvent.postValue(FieldValidationError.FIRST_NAME_REQUIRED)
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

    enum class FieldValidationError(val errorMessageId: Int) {
        FIRST_NAME_REQUIRED(R.string.required),
    }
}
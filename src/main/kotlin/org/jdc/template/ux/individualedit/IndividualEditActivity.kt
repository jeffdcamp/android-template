package org.jdc.template.ux.individualedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_individual_edit.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.jdc.template.R
import org.jdc.template.datasource.database.converter.ThreeTenFormatter
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.BaseActivity
import org.jdc.template.util.CoroutineContextProvider
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import javax.inject.Inject

class IndividualEditActivity : BaseActivity() {

    @Inject
    lateinit var cc: CoroutineContextProvider

    private lateinit var individualEditViewModel: IndividualEditViewModel

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_edit)
        individualEditViewModel = ViewModelProviders.of(this).get(IndividualEditViewModel::class.java)

        setupActionBar()

        alarmTimeEditText.setOnClickListener {
            showAlarmTimeSelector(individualEditViewModel.individual?.alarmTime ?: LocalTime.now())
        }

        birthDateEditText.setOnClickListener {
            showBirthDateSelector(individualEditViewModel.individual?.birthDate ?: LocalDate.now())
        }

        loadIndividual()
    }

    private fun loadIndividual() {
        addJob(launch(cc.ui) {
            val individual = run(context + cc.commonPool) {
                with(IntentOptions) {
                    individualEditViewModel.loadIndividual(intent.individualId)
                }
            }

            showIndividual(individual)
        })
    }

    private fun setupActionBar() {
        setSupportActionBar(mainToolbar)
        enableActionBarBackArrow(true)
        supportActionBar?.setTitle(R.string.individual)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.individual_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_item_save -> {
                saveIndividual()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            individualEditViewModel.individual?.let {
                it.birthDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth) // + 1 because cord Java Date is 0 based
            }
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because cord Java Date is 0 based

        birthDatePickerDialog.show()
    }

    fun showBirthDate(date: LocalDate) {
        val millis = ThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
        birthDateEditText.setText(DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR))
    }

    fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            individualEditViewModel.individual?.let {
                it.alarmTime = LocalTime.of(hourOfDay, minute)
            }
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }

    fun showAlarmTime(time: LocalTime) {
        val millis = ThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))
        alarmTimeEditText.setText(DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_TIME))
    }

    fun showIndividual(individual: Individual?) {
        individual ?: return

        firstNameEditText.setText(individual.firstName)
        lastNameEditText.setText(individual.lastName)
        emailEditText.setText(individual.email)
        phoneEditText.setText(individual.phone)

        individual.birthDate?.let {
            showBirthDate(it)
        }

        showAlarmTime(individual.alarmTime)
    }

    fun validateIndividualData(): Boolean {
        if (firstNameEditText.text.isBlank()) {
            firstNameLayout.error = getString(R.string.required)
            return false
        }

        return true
    }

    fun saveIndividual() {
        addJob(launch(cc.ui) {
            if (!validateIndividualData()) {
                return@launch
            }

            individualEditViewModel.individual?.let { individual ->
                individual.firstName = firstNameEditText.text.toString()
                individual.lastName = lastNameEditText.text.toString()
                individual.phone = phoneEditText.text.toString()
                individual.email = emailEditText.text.toString()

                run(context + cc.commonPool) {
                    individualEditViewModel.saveIndividual()
                }

                finish()
            }
        })
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, IndividualEditActivity::class)

    object IntentOptions {
        var Intent.individualId by IntentExtra.Long(defaultValue = -1L)
    }
}
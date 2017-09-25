package org.jdc.template.ux.individualedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProvider
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
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(IndividualEditViewModel::class.java) }

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_edit)

        setupActionBar()

        setupClickListeners()

        val individual = viewModel.individual
        if (savedInstanceState == null || individual == null) {
            with(IntentOptions) {
                loadIndividual(intent.individualId)
            }
        } else {
            showIndividual(individual)
        }
    }

    private fun setupClickListeners() {
        alarmTimeEditText.setOnClickListener {
            showAlarmTimeSelector(viewModel.individual?.alarmTime ?: LocalTime.now())
        }

        birthDateEditText.setOnClickListener {
            showBirthDateSelector(viewModel.individual?.birthDate ?: LocalDate.now())
        }
    }

    private fun loadIndividual(individualId: Long) {
        addJob(launch(cc.ui) {
            val individual = run(coroutineContext + cc.commonPool) {
                viewModel.loadIndividual(individualId)
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
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.menu_item_save -> {
                saveIndividual()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            viewModel.individual?.let {
                it.birthDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth) // + 1 because cord Java Date is 0 based
            }
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because cord Java Date is 0 based

        birthDatePickerDialog.show()
    }

    private fun showBirthDate(date: LocalDate) {
        val millis = ThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
        birthDateEditText.setText(DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR))
    }

    private fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.individual?.let {
                it.alarmTime = LocalTime.of(hourOfDay, minute)
            }
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }

    private fun showAlarmTime(time: LocalTime) {
        val millis = ThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))
        alarmTimeEditText.setText(DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_TIME))
    }

    private fun showIndividual(individual: Individual) {
        firstNameEditText.setText(individual.firstName)
        lastNameEditText.setText(individual.lastName)
        emailEditText.setText(individual.email)
        phoneEditText.setText(individual.phone)

        individual.birthDate?.let {
            showBirthDate(it)
        }

        showAlarmTime(individual.alarmTime)
    }

    private fun validateIndividualData(): Boolean {
        if (firstNameEditText.text.isBlank()) {
            firstNameLayout.error = getString(R.string.required)
            return false
        }

        return true
    }

    private fun saveIndividual() {
        addJob(launch(cc.ui) {
            if (!validateIndividualData()) {
                return@launch
            }

            viewModel.individual?.let { individual ->
                individual.firstName = firstNameEditText.text.toString()
                individual.lastName = lastNameEditText.text.toString()
                individual.phone = phoneEditText.text.toString()
                individual.email = emailEditText.text.toString()

                run(coroutineContext + cc.commonPool) {
                    viewModel.saveIndividual()
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
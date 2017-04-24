package org.jdc.template.ux.individualedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_individual_edit.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.jdc.template.R
import org.jdc.template.inject.Injector
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.ui.activity.BaseActivity
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import pocketknife.PocketKnife
import javax.inject.Inject

class IndividualEditActivity : BaseActivity(), IndividualEditContract.View {

    @Inject
    lateinit var presenter: IndividualEditPresenter

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_edit)
        PocketKnife.bindExtras(this)

        setupActionBar()

        alarmTimeEditText.setOnClickListener {
            presenter.alarmTimeClicked()
        }

        birthDateEditText.setOnClickListener {
            presenter.birthdayClicked()
        }


        val individualId = intent.getLongExtra(IndividualEditContract.Extras.EXTRA_ID, 0L)
        presenter.init(this, individualId)
        presenter.load()
    }

    override fun onStop() {
        presenter.unregister()
        super.onStop()
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
                presenter.saveIndividual()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            presenter.birthDateSelected(LocalDate.of(year, monthOfYear + 1, dayOfMonth)) // + 1 because cord Java Date is 0 based
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because cord Java Date is 0 based

        birthDatePickerDialog.show()
    }

    override fun showBirthDate(date: LocalDate) {
        val millis = DBToolsThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
        birthDateEditText.setText(DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR))
    }

    override fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            presenter.alarmTimeSelected(LocalTime.of(hourOfDay, minute))
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }

    override fun showAlarmTime(time: LocalTime) {
        val millis = DBToolsThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))
        alarmTimeEditText.setText(DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_TIME))
    }

    override fun showIndividual(individual: Individual) {
        firstNameEditText.setText(individual.firstName)
        lastNameEditText.setText(individual.lastName)
        emailEditText.setText(individual.email)
        phoneEditText.setText(individual.phone)

        individual.birthDate?.let {
            showBirthDate(it)
        }

        showAlarmTime(individual.alarmTime)
    }

    override fun validateIndividualData(): Boolean {
        if (firstNameEditText.text.isBlank()) {
            firstNameLayout.error = getString(R.string.required)
            return false
        }

        return true
    }

    override fun getIndividualDataFromUi(individual: Individual) {
        individual.firstName = firstNameEditText.text.toString()
        individual.lastName = lastNameEditText.text.toString()
        individual.phone = phoneEditText.text.toString()
        individual.email = emailEditText.text.toString()
    }

    override fun close() {
        finish()
    }
}
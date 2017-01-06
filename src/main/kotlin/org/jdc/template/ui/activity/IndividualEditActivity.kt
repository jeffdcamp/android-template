package org.jdc.template.ui.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.individual_edit.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.jdc.template.Analytics
import org.jdc.template.R
import org.jdc.template.inject.Injector
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.model.database.main.individual.IndividualManager
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import pocketknife.BindExtra
import pocketknife.PocketKnife
import javax.inject.Inject

class IndividualEditActivity : BaseActivity() {

    @BindExtra(EXTRA_ID)
    var individualId = 0L

    @Inject
    lateinit var individualManager: IndividualManager
    @Inject
    lateinit var analytics: Analytics

    private var individual = Individual()

    private var birthDatePickerDialog: DatePickerDialog? = null
    private var alarmTimePickerDialog: TimePickerDialog? = null

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_edit)
        PocketKnife.bindExtras(this)

        setSupportActionBar(ab_toolbar)
        enableActionBarBackArrow(true)

        setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL)

        setupActionBar()

        alarmTimeEditText.setOnClickListener {
            onAlarmClick()
        }

        birthDateEditText.setOnClickListener {
            onBirthdayClick()
        }

        showIndividual()

    }

    private fun setupActionBar() {
        setSupportActionBar(ab_toolbar)
        val actionBar = supportActionBar

        actionBar?.setTitle(R.string.individual)
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

    fun onBirthdayClick() {
        if (birthDatePickerDialog == null) {

            val date = if (individual.birthDate != null) individual.birthDate else LocalDate.now()
            if (date != null) {
                birthDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    individual.birthDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth) // + 1 because cord Java Date is 0 based
                    showBirthDate()
                }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because cord Java Date is 0 based
            }
        }

        birthDatePickerDialog!!.show()
    }

    fun onAlarmClick() {
        if (alarmTimePickerDialog == null) {
            val time = if (individual.alarmTime != null) individual.alarmTime else LocalTime.now()
            alarmTimePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                individual.alarmTime = LocalTime.of(hourOfDay, minute)
                showAlarmTime()
            }, time.hour, time.minute, false)
        }

        alarmTimePickerDialog!!.show()
    }

    private fun showIndividual() {
        if (individualId <= 0) {
            return
        }

        val foundIndividual = individualManager.findByRowId(individualId)
        if (foundIndividual != null) {
            individual = foundIndividual
            analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_EDIT).build())

            firstNameEditText.setText(individual.firstName)
            lastNameEditText.setText(individual.lastName)
            emailEditText.setText(individual.email)
            phoneEditText.setText(individual.phone)

            showBirthDate()
            showAlarmTime()
        }
    }

    private fun showBirthDate() {
        if (individual.birthDate == null) {
            return
        }

        val date = individual.birthDate
        if (date != null) {
            val millis = DBToolsThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
            birthDateEditText.setText(DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR))
        }
    }

    private fun showAlarmTime() {
        if (individual.alarmTime == null) {
            return
        }

        val time = individual.alarmTime
        val millis = DBToolsThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))
        alarmTimeEditText.setText(DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_TIME))
    }

    private fun saveIndividual() {
        if (firstNameEditText.text.isBlank()) {
            firstNameLayout.error = getString(R.string.required)
            return
        }

        if (individual.alarmTime == null) {
            alarmTimeLayout.error = getString(R.string.required)
            return
        }

        individual.firstName = firstNameEditText.text.toString()
        individual.lastName = lastNameEditText.text.toString()
        individual.phone = phoneEditText.text.toString()
        individual.email = emailEditText.text.toString()

        individualManager.save(individual)

        finish()
    }

    companion object {

        const val EXTRA_ID = "INDIVIDUAL_ID"
    }
}
package org.jdc.template.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.*
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.fragment_individual_edit.*
import org.apache.commons.lang3.StringUtils
import org.dbtools.android.domain.DBToolsDateFormatter
import org.jdc.template.Analytics
import org.jdc.template.App
import org.jdc.template.R
import org.jdc.template.R.layout.fragment_individual_edit
import org.jdc.template.dagger.Injector
import org.jdc.template.domain.main.individual.Individual
import org.jdc.template.domain.main.individual.IndividualManager
import org.jdc.template.event.IndividualSavedEvent
import org.jdc.template.event.RxBus
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import pocketknife.BindArgument
import pocketknife.PocketKnife
import javax.inject.Inject

class IndividualEditFragment : Fragment() {

    @Inject
    lateinit var individualManager: IndividualManager
    @Inject
    lateinit var bus: RxBus
    @Inject
    lateinit var analytics: Analytics

    @BindArgument(ARG_ID)
    var individualId: Long = 0

    private var individual = Individual()

    private var birthDatePickerDialog: DatePickerDialog? = null
    private var alarmTimePickerDialog: TimePickerDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)
        PocketKnife.bindArguments(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(fragment_individual_edit, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alarmTimeEditText.setOnClickListener() {
            onAlarmClick()
        }

        birthDateEditText.setOnClickListener() {
            onBirthdayClick()
        }

        showIndividual()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.individual_edit_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
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
                birthDatePickerDialog = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    individual.birthDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth) // + 1 because cord Java Date is 0 based
                    showBirthDate()
                }, date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth()) // - 1 because cord Java Date is 0 based
            }
        }

        birthDatePickerDialog!!.show()
    }

    fun onAlarmClick() {
        if (alarmTimePickerDialog == null) {
            val time = if (individual.alarmTime != null) individual.alarmTime else LocalTime.now()
            alarmTimePickerDialog = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
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
            val millis = DBToolsDateFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
            birthDateEditText.setText(DateUtils.formatDateTime(activity, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR))
        }
    }

    private fun showAlarmTime() {
        if (individual.alarmTime == null) {
            return
        }

        val time = individual.alarmTime
        val millis = DBToolsDateFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))
        alarmTimeEditText.setText(DateUtils.formatDateTime(activity, millis!!, DateUtils.FORMAT_SHOW_TIME))
    }

    private fun saveIndividual() {
        if (StringUtils.isBlank(firstNameEditText.text)) {
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

        bus.post(IndividualSavedEvent(individual.id))
        bus.post(IndividualSavedEvent(individual.id))
    }

    companion object {
        val TAG = App.createTag(IndividualEditFragment::class.java)

        const val ARG_ID = "ID"

        fun newInstance(individualId: Long): IndividualEditFragment {
            val fragment = IndividualEditFragment()
            val args = Bundle()
            args.putLong(ARG_ID, individualId)
            fragment.arguments = args
            return fragment
        }
    }
}

package org.jdc.template.ui.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateUtils
import android.view.*
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.fragment_individual.*
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.jdc.template.Analytics
import org.jdc.template.App
import org.jdc.template.R
import org.jdc.template.R.layout.fragment_individual
import org.jdc.template.event.EditIndividualEvent
import org.jdc.template.event.IndividualDeletedEvent
import org.jdc.template.inject.Injector
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.model.database.main.individual.IndividualManager
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import pocketbus.Bus
import pocketknife.BindArgument
import pocketknife.PocketKnife
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class IndividualFragment : Fragment() {

    @Inject
    lateinit  var individualManager: IndividualManager
    @Inject
    lateinit var bus: Bus
    @Inject
    lateinit var analytics: Analytics

    @BindArgument(ARG_ID)
    var individualId: Long = 0

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PocketKnife.bindArguments(this)

        // todo fix issue with Pocketknife (this should be initialized by @BindArgument)
        individualId = arguments.getLong(ARG_ID)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(fragment_individual, container, false)

        showIndividual()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.individual_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_item_edit -> {
                bus.post(EditIndividualEvent(individualId))
                return true
            }
            R.id.menu_item_delete -> {
                deleteIndividual()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        showIndividual()
    }

    private fun showIndividual() {
        if (individualId <= 0) {
            return
        }

        individualManager.findByRowIdRx(individualId).subscribeOn(Schedulers.io()).filter { individual -> individual != null }.observeOn(AndroidSchedulers.mainThread()).subscribe { individual -> setUi(individual) }
    }

    private fun setUi(individual: Individual) {
        analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_VIEW).build())

        if (!this.isVisible) {
            return;
        }

        nameTextView.text = individual.getFullName()
        phoneTextView.text = individual.phone
        emailTextView.text = individual.email
        showBirthDate(individual)
        showAlarmTime(individual)
        showSampleDateTime(individual)
    }

    private fun showBirthDate(individual: Individual) {
        if (individual.birthDate == null) {
            return
        }

        val date = individual.birthDate

        if (date != null) {
            val millis = DBToolsThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
            birthDateTextView.text = DateUtils.formatDateTime(activity, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)
        }
    }

    private fun showAlarmTime(individual: Individual) {
        val time = individual.alarmTime
        val millis = DBToolsThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))
        alarmTimeTextView.text = DateUtils.formatDateTime(activity, millis!!, DateUtils.FORMAT_SHOW_TIME)
    }

    private fun showSampleDateTime(individual: Individual) {
        if (individual.sampleDateTime == null) {
            return
        }

        val dateTime = individual.sampleDateTime

        if (dateTime != null) {
            val millis = DBToolsThreeTenFormatter.localDateTimeToLong(dateTime)
            sampleDateTimeTextView.text = DateUtils.formatDateTime(activity, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_TIME)
        }
    }

    private fun deleteIndividual() {
        AlertDialog.Builder(activity).setMessage(R.string.delete_individual_confirm).setPositiveButton(R.string.delete) { dialog, which ->
            individualManager.delete(individualId)

            analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_DELETE).build())

            bus.post(IndividualDeletedEvent(individualId))
        }.setNegativeButton(R.string.cancel, null).show()
    }

    companion object {
        val TAG = App.createTag(IndividualFragment::class.java)

        const val ARG_ID = "ID"

        fun newInstance(individualId: Long): IndividualFragment {
            val fragment = IndividualFragment()
            val args = Bundle()
            args.putLong(ARG_ID, individualId)
            fragment.arguments = args
            return fragment
        }
    }
}

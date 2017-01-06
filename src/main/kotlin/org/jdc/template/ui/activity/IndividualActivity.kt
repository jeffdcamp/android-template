package org.jdc.template.ui.activity

import android.app.AlertDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.analytics.HitBuilders
import kotlinx.android.synthetic.main.individual.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.jdc.template.Analytics
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.R.layout.activity_individual
import org.jdc.template.inject.Injector
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.model.database.main.individual.IndividualManager
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import pocketknife.BindExtra
import pocketknife.PocketKnife
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class IndividualActivity : DrawerActivity() {

    @BindExtra(EXTRA_ID)
    var individualId = 0L

    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var individualManager: IndividualManager

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_individual)
        PocketKnife.bindExtras(this)

        setupDrawerWithBackButton(ab_toolbar, R.string.individual)

        showIndividual()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.individual_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_edit -> {
                internalIntents.editIndividual(this, individualId)
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
            birthDateTextView.text = DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)
        }
    }

    private fun showAlarmTime(individual: Individual) {
        val time = individual.alarmTime
        val millis = DBToolsThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))
        alarmTimeTextView.text = DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_TIME)
    }

    private fun showSampleDateTime(individual: Individual) {
        if (individual.sampleDateTime == null) {
            return
        }

        val dateTime = individual.sampleDateTime

        if (dateTime != null) {
            val millis = DBToolsThreeTenFormatter.localDateTimeToLong(dateTime)
            sampleDateTimeTextView.text = DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_TIME)
        }
    }

    private fun deleteIndividual() {
        AlertDialog.Builder(this).setMessage(R.string.delete_individual_confirm).setPositiveButton(R.string.delete) { dialog, which ->
            individualManager.delete(individualId)

            analytics.send(HitBuilders.EventBuilder().setCategory(Analytics.CATEGORY_INDIVIDUAL).setAction(Analytics.ACTION_DELETE).build())

            finish()
        }.setNegativeButton(R.string.cancel, null).show()
    }

    companion object {
        const val EXTRA_ID = "INDIVIDUAL_ID"
    }
}
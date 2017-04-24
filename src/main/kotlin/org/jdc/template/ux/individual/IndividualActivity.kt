package org.jdc.template.ux.individual

import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_individual.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.R.layout.activity_individual
import org.jdc.template.inject.Injector
import org.jdc.template.model.database.main.individual.Individual
import org.jdc.template.ui.activity.BaseActivity
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import pocketknife.PocketKnife
import javax.inject.Inject

class IndividualActivity : BaseActivity(), IndividualContract.View {

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var presenter: IndividualPresenter

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_individual)
        PocketKnife.bindExtras(this)

        setupActionBar()

        val individualId = intent.getLongExtra(IndividualContract.Extras.EXTRA_ID, -1)
        presenter.init(this, individualId)
        presenter.load()
    }

    private fun setupActionBar() {
        setSupportActionBar(mainToolbar)
        enableActionBarBackArrow(true)
        supportActionBar?.setTitle(R.string.individual)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.individual_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_edit -> {
                presenter.editIndividualClicked()
                return true
            }
            R.id.menu_item_delete -> {
                presenter.deleteIndividualClicked()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun promptDeleteIndividual() {
        android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(R.string.delete_individual_confirm)
                .setPositiveButton(R.string.delete) { _, _ -> presenter.deleteIndividual() }
                .setNegativeButton(R.string.cancel, null)
                .show()
    }

    override fun onResume() {
        super.onResume()
        presenter.reload()
    }

    override fun onStop() {
        presenter.unregister()
        super.onStop()
    }

    override fun close() {
        finish()
    }

    override fun showIndividual(individual: Individual) {
        nameTextView.text = individual.getFullName()
        phoneTextView.text = individual.phone
        emailTextView.text = individual.email
        showBirthDate(individual)
        showAlarmTime(individual)
        showSampleDateTime(individual)
    }

    override fun showEditIndividual(individualId: Long) {
        internalIntents.editIndividual(this, individualId)
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
}
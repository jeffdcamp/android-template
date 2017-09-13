package org.jdc.template.ux.individual

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_individual.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.R.layout.activity_individual
import org.jdc.template.datasource.database.converter.ThreeTenFormatter
import org.jdc.template.datasource.database.main.individual.Individual
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.BaseActivity
import org.jdc.template.util.CoroutineContextProvider
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import javax.inject.Inject

class IndividualActivity : BaseActivity() {

    @Inject
    lateinit var cc: CoroutineContextProvider
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: IndividualViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(IndividualViewModel::class.java) }

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_individual)

        setupActionBar()

        viewModel.individual.observe(this@IndividualActivity, Observer { individual ->
            showIndividual(individual)
        })

        with(IntentOptions) {
            viewModel.setIndividualId(intent.individualId)
        }
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
        return when (item.itemId) {
            R.id.menu_item_edit -> {
                showEditIndividual()
                true
            }
            R.id.menu_item_delete -> {
                promptDeleteIndividual()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun promptDeleteIndividual() {
        android.support.v7.app.AlertDialog.Builder(this)
                .setMessage(R.string.delete_individual_confirm)
                .setPositiveButton(R.string.delete) { _, _ -> deleteIndividual() }
                .setNegativeButton(R.string.cancel, null)
                .show()
    }

    private fun showIndividual(individual: Individual?) {
        individual ?: return

        nameTextView.text = individual.getFullName()
        phoneTextView.text = individual.phone
        emailTextView.text = individual.email
        showBirthDate(individual)
        showAlarmTime(individual)
    }

    private fun showEditIndividual() {
        with(IntentOptions) {
            internalIntents.editIndividual(this@IndividualActivity, intent.individualId)
        }
    }

    private fun showBirthDate(individual: Individual) {
        if (individual.birthDate == null) {
            return
        }

        val date = individual.birthDate

        if (date != null) {
            val millis = ThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
            birthDateTextView.text = DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)
        }
    }

    private fun showAlarmTime(individual: Individual) {
        val time = individual.alarmTime
        val millis = ThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))
        alarmTimeTextView.text = DateUtils.formatDateTime(this, millis!!, DateUtils.FORMAT_SHOW_TIME)
    }

    private fun deleteIndividual() {
        launch(cc.ui) {
            run(coroutineContext + cc.commonPool) {
                with(IntentOptions) {
                    viewModel.deleteIndividual(intent.individualId)
                }
            }

            finish()
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, IndividualActivity::class)

    object IntentOptions {
        var Intent.individualId by IntentExtra.Long(defaultValue = 0L)
    }
}
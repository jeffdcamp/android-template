package org.jdc.template.ux.individual

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_individual.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
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
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import javax.inject.Inject

class IndividualActivity : BaseActivity() {

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

        setupViewModelObservers()

        with(IntentOptions) {
            viewModel.setIndividualId(intent.individualId)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.individual.observeNotNull { individual ->
            showIndividual(individual)
        }

        // Events
        viewModel.onEditIndividualEvent.observeNotNull { individualId ->
            internalIntents.editIndividual(this@IndividualActivity, individualId)
        }
        viewModel.onIndividualDeletedEvent.observe {
            finish()
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
                viewModel.editTask()
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
        MaterialDialog.Builder(this)
                .content(R.string.delete_individual_confirm)
                .positiveText(R.string.delete)
                .onPositive({_,_ -> viewModel.deleteTask()})
                .negativeText(R.string.cancel)
                .show()
    }

    private fun showIndividual(individual: Individual) {
        nameTextView.text = individual.getFullName()
        phoneTextView.text = individual.phone
        emailTextView.text = individual.email
        showBirthDate(individual)
        showAlarmTime(individual)
    }

    private fun showBirthDate(individual: Individual) {
        individual.birthDate?.let { date ->
            ThreeTenFormatter.localDateTimeToLong(date.atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())?.let { millis ->
                birthDateTextView.text = DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)
            }
        }
    }

    private fun showAlarmTime(individual: Individual) {
        val time = individual.alarmTime
        ThreeTenFormatter.localDateTimeToLong(time.atDate(LocalDate.now()))?.let { millis ->
            alarmTimeTextView.text = DateUtils.formatDateTime(this, millis, DateUtils.FORMAT_SHOW_TIME)
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, IndividualActivity::class)

    object IntentOptions {
        var Intent.individualId by IntentExtra.Long(defaultValue = 0L)
    }
}
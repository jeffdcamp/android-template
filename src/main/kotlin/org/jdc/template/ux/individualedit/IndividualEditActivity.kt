package org.jdc.template.ux.individualedit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.jdc.template.R
import org.jdc.template.databinding.IndividualEditActivityBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.BaseActivity
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

class IndividualEditActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(IndividualEditViewModel::class.java) }
    private lateinit var binding: IndividualEditActivityBinding

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.individual_edit_activity)
        binding.apply {
            viewModel = this@IndividualEditActivity.viewModel
        }

        setupActionBar()

        setupViewModelObservers()

        with(IntentOptions) {
            viewModel.loadIndividual(intent.individualId)
        }
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onIndividualSavedEvent.observe { finish() }
        viewModel.onShowBirthDateSelectionEvent.observeNotNull { showBirthDateSelector(it) }
        viewModel.onShowAlarmTimeSelectionEvent.observeNotNull { showAlarmTimeSelector(it) }

        viewModel.onValidationSaveErrorEvent.observeNotNull {
            when (it) {
                IndividualEditViewModel.RequiredField.FIRST_NAME -> binding.firstNameLayout.error = getString(it.errorMessageId)
            }
        }

    }

    private fun setupActionBar() {
        setSupportActionBar(findViewById(R.id.mainToolbar))
        enableActionBarBackArrow(true)
        supportActionBar?.setTitle(R.string.individual)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.individual_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_save -> {
                viewModel.saveIndividual()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showBirthDateSelector(date: LocalDate) {
        val birthDatePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            viewModel.birthDate.set(LocalDate.of(year, monthOfYear + 1, dayOfMonth)) // + 1 because cord Java Date is 0 based
        }, date.year, date.monthValue - 1, date.dayOfMonth) // - 1 because cord Java Date is 0 based

        birthDatePickerDialog.show()
    }

    private fun showAlarmTimeSelector(time: LocalTime) {
        val alarmTimePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            viewModel.alarmTime.set(LocalTime.of(hourOfDay, minute))
        }, time.hour, time.minute, false)

        alarmTimePickerDialog.show()
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, IndividualEditActivity::class)

    object IntentOptions {
        var Intent.individualId by IntentExtra.Long(defaultValue = -1L)
    }
}
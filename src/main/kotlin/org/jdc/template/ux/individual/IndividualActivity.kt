package org.jdc.template.ux.individual

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.jdc.template.InternalIntents
import org.jdc.template.R
import org.jdc.template.databinding.IndividualActivityBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.BaseActivity
import javax.inject.Inject

class IndividualActivity : BaseActivity() {

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: IndividualViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(IndividualViewModel::class.java) }
    private lateinit var binding: IndividualActivityBinding

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.individual_activity)
        binding.apply {
            viewModel = this@IndividualActivity.viewModel
            setLifecycleOwner(this@IndividualActivity)
        }

        setupActionBar()

        setupViewModelObservers()

        with(IntentOptions) {
            viewModel.setIndividualId(intent.individualId)
        }
    }

    private fun setupViewModelObservers() {
        // Events
        viewModel.onEditIndividualEvent.observeNotNull { individualId ->
            internalIntents.editIndividual(this@IndividualActivity, individualId)
        }
        viewModel.onIndividualDeletedEvent.observe {
            finish()
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(findViewById(R.id.mainToolbar))
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

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, IndividualActivity::class)

    object IntentOptions {
        var Intent.individualId by IntentExtra.Long(defaultValue = 0L)
    }
}
package org.jdc.template.ux.about

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import me.eugeniomarletti.extras.ActivityCompanion
import org.jdc.template.R
import org.jdc.template.databinding.AboutActivityBinding
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.BaseActivity
import javax.inject.Inject

class AboutActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AboutViewModel::class.java) }
    private val binding by lazy { DataBindingUtil.setContentView<AboutActivityBinding>(this, R.layout.about_activity) }

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            viewModel = this@AboutActivity.viewModel
            setLifecycleOwner(this@AboutActivity)
        }

        viewModel.logAnalytics()

        setSupportActionBar(binding.appbar.mainToolbar)
        enableActionBarBackArrow(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_licenses -> {
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.menu_lib_licenses))
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return true
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, AboutActivity::class)

    object IntentOptions
}

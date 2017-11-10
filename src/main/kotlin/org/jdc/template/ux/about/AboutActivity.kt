package org.jdc.template.ux.about

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import me.eugeniomarletti.extras.ActivityCompanion
import org.jdc.template.BuildConfig
import org.jdc.template.R
import org.jdc.template.R.layout.activity_about
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class AboutActivity : BaseActivity() {

    @Inject
    lateinit var individualDao: IndividualDao
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AboutViewModel::class.java) }

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_about)
        viewModel.logAnalytics()

        setSupportActionBar(mainToolbar)
        enableActionBarBackArrow(true)

        versionTextView.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        versionDateTextView.text = DateUtils.formatDateTime(this, BuildConfig.BUILD_TIME, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)

        createDatabaseButton.setOnClickListener {
            viewModel.createSampleDataWithInjection()

            // OR
//            viewModel.createSampleDataNoInjection()
        }
        restTestButton.setOnClickListener {
            viewModel.testQueryWebServiceCall() // simple rest call
//            viewModel.testQueryWebServiceCallRx() // use Rx to make the call
//            viewModel.testSaveQueryWebServiceCall() // write the response to file, the read the file to show results
//            viewModel.testFullUrlQueryWebServiceCall() //  simple call using the full URL instead of an endpoint
        }
        jobTestButton.setOnClickListener {
            viewModel.jobTest()
        }
        textTableChangeButton.setOnClickListener {
            viewModel.testTableChange()
        }
        testButton.setOnClickListener {
        }

        individualDao.findAllLive().observe(this, Observer { list ->
            Timber.i("==== Individual Table Changed ====")
            list?.forEach { individual ->
                Timber.i("Individual [${individual.getFullName()}]")
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> finish()
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

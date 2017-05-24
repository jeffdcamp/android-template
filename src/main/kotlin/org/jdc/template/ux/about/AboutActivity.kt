package org.jdc.template.ux.about

import android.R
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.format.DateUtils
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import me.eugeniomarletti.extras.ActivityCompanion
import org.jdc.template.BuildConfig
import org.jdc.template.R.layout.activity_about
import org.jdc.template.datasource.database.main.individual.IndividualDao
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class AboutActivity : BaseActivity() {

    @Inject
    lateinit var controller: AboutController
    @Inject
    lateinit var individualDao: IndividualDao

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_about)

        setSupportActionBar(mainToolbar)
        enableActionBarBackArrow(true)

        versionTextView.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        versionDateTextView.text = DateUtils.formatDateTime(this, BuildConfig.BUILD_TIME, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)

        createDatabaseButton.setOnClickListener {
            controller.createSampleDataWithInjection()

            // OR
//            controller.createSampleDataNoInjection()
        }
        restTestButton.setOnClickListener {
//            controller.testQueryWebServiceCall() // simple rest call
            controller.testQueryWebServiceCallRx() // use Rx to make the call
//            controller.testSaveQueryWebServiceCall() // write the response to file, the read the file to show results
//            controller.testFullUrlQueryWebServiceCall() //  simple call using the full URL instead of an endpoint
        }
        jobTestButton.setOnClickListener {
            controller.jobTest()
        }
        textTableChangeButton.setOnClickListener {
            controller.testTableChange()
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

    override fun onStart() {
        super.onStart()
        controller.register()
    }

    override fun onStop() {
        controller.unregister()
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, AboutActivity::class)

    object IntentOptions
}

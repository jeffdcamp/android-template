package org.jdc.template.ux.about

import android.R
import android.os.Bundle
import android.text.format.DateUtils
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.jdc.template.BuildConfig
import org.jdc.template.R.layout.activity_about
import org.jdc.template.inject.Injector
import org.jdc.template.ui.activity.BaseActivity
import javax.inject.Inject

class AboutActivity : BaseActivity(), AboutContract.View {

    @Inject
    lateinit var presenter: AboutPresenter

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_about)
        presenter.init(this)

        setSupportActionBar(mainToolbar)
        enableActionBarBackArrow(true)

        versionTextView.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        versionDateTextView.text = DateUtils.formatDateTime(this, BuildConfig.BUILD_TIME, DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_YEAR)

        createDatabaseButton.setOnClickListener {
            presenter.createSampleDataWithInjection()

            // OR
//            presenter.createSampleDataNoInjection()
        }
        restTestButton.setOnClickListener {
//            presenter.testQueryWebServiceCall() // simple rest call
            presenter.testQueryWebServiceCallRx() // use Rx to make the call
//            presenter.testSaveQueryWebServiceCall() // write the response to file, the read the file to show results
//            presenter.testFullUrlQueryWebServiceCall() //  simple call using the full URL instead of an endpoint
        }
        jobTestButton.setOnClickListener {
            presenter.jobTest()
        }
        rxTestButton.setOnClickListener {
            presenter.testRx()
        }
        testButton.setOnClickListener {
//            testQuery()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.register()
    }

    override fun onStop() {
        presenter.unregister()
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
}

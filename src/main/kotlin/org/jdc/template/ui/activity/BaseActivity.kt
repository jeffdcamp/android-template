package org.jdc.template.ui.activity

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.coroutines.experimental.Job
import org.jdc.template.util.CompositeJob

open class BaseActivity : AppCompatActivity() {

    private val compositeJob = CompositeJob()

    override fun onStop() {
        compositeJob.cancelAndClearAll()
        super.onStop()
    }

    fun addJob(job: Job) {
        compositeJob.add(job)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (allowFinishOnHome() && item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun allowFinishOnHome(): Boolean {
        return true
    }

    fun enableActionBarBackArrow(enable: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enable)
            actionBar.setDisplayHomeAsUpEnabled(enable)
        }
    }
}

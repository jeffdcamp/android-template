package org.jdc.template.ui.activity

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.coroutines.experimental.Job

abstract class BaseActivity : AppCompatActivity() {

    private var compositeJob: Job? = null

    override fun onStop() {
        compositeJob?.cancel()
        compositeJob = null
        super.onStop()
    }

    fun addJob(job: Job) {
        if (compositeJob == null) {
            compositeJob = Job()
        }
        compositeJob?.attachChild(job)
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

package org.jdc.template.ui.activity

import android.view.MenuItem

abstract class BaseActivity : LiveDataObserverActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (allowFinishOnHome() && item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    protected open fun allowFinishOnHome() = true

    fun enableActionBarBackArrow(enable: Boolean) {
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(enable)
            actionBar.setDisplayHomeAsUpEnabled(enable)
        }
    }
}

package org.jdc.template.ui.activity

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.Job
import org.jdc.template.util.CompositeJob

open class BaseActivity : AppCompatActivity() {

    private var compositeDisposable = CompositeDisposable()
    private val compositeFuture = CompositeJob()

    override fun onStop() {
        compositeFuture.cancelAndClearAll()
        compositeDisposable.dispose()
        super.onStop()
    }

    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(disposable)
    }

    fun addJob(job: Job) {
        compositeFuture.add(job)
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

package org.jdc.template.ux.startup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import org.jdc.template.R
import org.jdc.template.inject.Injector
import org.jdc.template.ux.directory.DirectoryActivity
import javax.inject.Inject

class StartupActivity : Activity(), StartupContract.View {

    @Inject
    lateinit var presenter: StartupPresenter

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.init(this)
        presenter.load()
    }

    override fun onStop() {
        presenter.unregister()
        super.onStop()
    }

    override fun showStartActivity() {
        val intent = Intent(application, DirectoryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)

        startActivity(intent)
    }

    override fun close() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.nothing) // no animation
    }
}

package org.jdc.template.ux.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.jdc.template.R;
import org.jdc.template.inject.Injector;
import org.jdc.template.ux.directory.DirectoryActivity;

import javax.inject.Inject;

public class StartupActivity extends Activity implements StartupContract.View {
    @Inject
    StartupPresenter presenter;

    private final Class startupActivityClass = DirectoryActivity.class;

    public StartupActivity() {
        Injector.get().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter.init(this);
        presenter.load();
    }

    @Override
    public void showStartActivity() {
        Intent intent = new Intent(this, startupActivityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void close() {
        finish();
    }
}

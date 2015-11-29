package org.jdc.template.ui.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import org.jdc.template.dagger.Injector;
import org.jdc.template.domain.main.individual.IndividualManager;

import javax.inject.Inject;

public class DirectoryListLoader extends CursorLoader {
    @Inject
    IndividualManager individualManager;

    public DirectoryListLoader(Context context) {
        super(context);
        Injector.get().inject(this);
    }

    @Override
    public Cursor loadInBackground() {
        return individualManager.findCursorBySelection(null, null);
    }

}

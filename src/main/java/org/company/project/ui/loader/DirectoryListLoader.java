package org.company.project.ui.loader;

import android.app.Activity;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import org.company.project.App;
import org.company.project.domain.main.individual.IndividualManager;

import javax.inject.Inject;

public class DirectoryListLoader extends CursorLoader {
    @Inject
    IndividualManager individualManager;

    public DirectoryListLoader(Activity activity) {
        super(activity);
        App.getApplication(activity).inject(this);
    }

    @Override
    public Cursor loadInBackground() {
        return individualManager.findCursorBySelection(null, null);
    }

}

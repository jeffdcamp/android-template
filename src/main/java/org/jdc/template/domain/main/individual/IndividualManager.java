/*
 * IndividualManager.java
 *
 * Generated on: 10/02/2012 01:40:29
 *
 */



package org.jdc.template.domain.main.individual;


import org.jdc.template.domain.DatabaseManager;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

@javax.inject.Singleton
public class IndividualManager extends IndividualBaseManager {

    @Inject
    public IndividualManager(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @Override
    public boolean save(@Nonnull String databaseName, @Nullable Individual individual) {
        if (individual == null) {
            return false;
        }

        individual.setLastModified(DateTime.now());
        return super.save(databaseName, individual);
    }
}
/*
 * IndividualManager.java
 *
 * Generated on: 10/02/2012 01:40:29
 *
 */



package org.jdc.template.domain.main.individual;


import android.database.Cursor;

import org.jdc.template.util.RxUtil;

import javax.inject.Inject;

import rx.Observable;

@javax.inject.Singleton
public class IndividualManager extends IndividualBaseManager {

    @Inject
    public IndividualManager() {
    }

    public Cursor findCursorIndividuals() {
        return findCursorAll();
    }

    public Observable<Cursor> findCursorIndividualsRx() {
//        return findCursorAllRx(); // dbtoold 4.0.0
        return RxUtil.toObservable(() -> findCursorIndividuals());
    }
}
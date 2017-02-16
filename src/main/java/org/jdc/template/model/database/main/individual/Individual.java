/*
 * Individual.java
 *
 * Created: 10/02/2012 01:40:29
 */



package org.jdc.template.model.database.main.individual;

public class Individual extends IndividualBaseRecord {
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
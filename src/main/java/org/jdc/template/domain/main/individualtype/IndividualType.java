/*
 * IndividualType.java
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.jdc.template.domain.main.individualtype;

import java.util.Map;
import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.dbtools.android.domain.DBToolsDateFormatter;


@SuppressWarnings("all")
public enum IndividualType {
HEAD, SPOUSE, CHILD;

    private static Map<IndividualType, String> enumStringMap = new EnumMap<IndividualType, String>(IndividualType.class);
    private static List<String> stringList = new ArrayList<String>();

    static {
        enumStringMap.put(HEAD, "Head");
        stringList.add("Head");
        
        enumStringMap.put(SPOUSE, "Spouse");
        stringList.add("Spouse");
        
        enumStringMap.put(CHILD, "Child");
        stringList.add("Child");
        

    }

    public static String getString(IndividualType key) {
        return enumStringMap.get(key);
    }

    public static List<String> getList() {
        return Collections.unmodifiableList(stringList);
    }


}
package org.jdc.template.ux.individualedit;

import org.jdc.template.model.database.main.individual.Individual;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

public class IndividualEditContract {
    interface View {
        void showIndividual(Individual individual);
        boolean validateIndividualData();
        void getIndividualDataFromUi(Individual individual);
        void close();
        void showAlarmTimeSelector(LocalTime time);
        void showAlarmTime(LocalTime time);
        void showBirthDateSelector(LocalDate date);
        void showBirthDate(LocalDate date);
    }

    public class Extras {
        public static final String EXTRA_ID = "INDIVIDUAL_ID";
    }
}

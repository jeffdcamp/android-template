package org.jdc.template.ux.individual;

import org.jdc.template.model.database.main.individual.Individual;

public class IndividualContract {
    interface View {
        void showIndividual(Individual individual);

        void close();

        void showEditIndividual(long individualId);

        void promptDeleteIndividual();
    }

    public class Extras {
        public static final String EXTRA_ID = "INDIVIDUAL_ID";
    }
}

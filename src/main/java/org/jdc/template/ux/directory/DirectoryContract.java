package org.jdc.template.ux.directory;

import org.jdc.template.model.database.main.individual.Individual;

import java.util.List;

public class DirectoryContract {
    interface View {
        void showNewIndividual();
        void showIndividualList(List<Individual> list);
        void showIndividual(long individualId);
    }

    class Extras {
    }
}

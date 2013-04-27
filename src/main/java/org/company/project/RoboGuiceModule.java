package org.company.project;

import com.google.inject.AbstractModule;
import org.company.project.domain.DatabaseManager;

public class RoboGuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        requestStaticInjection(DatabaseManager.class);
    }
}

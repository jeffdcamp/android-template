package org.company.project.loader;

import dagger.Module;

@Module(
        injects = {
                PeopleListLoader.class,
        },
        complete = false
)
public class LoaderModule {
}

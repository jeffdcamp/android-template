package org.company.project.loader;

import dagger.Module;

@Module(
        injects = {
                DirectoryListLoader.class,
        },
        complete = false
)
public class LoaderModule {
}

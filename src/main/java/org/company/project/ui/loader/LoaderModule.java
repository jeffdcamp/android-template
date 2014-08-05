package org.company.project.ui.loader;

import dagger.Module;

@Module(
        injects = {
                DirectoryListLoader.class,
        },
        complete = false
)
public class LoaderModule {
}

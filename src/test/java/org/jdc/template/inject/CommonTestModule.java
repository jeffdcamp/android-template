package org.jdc.template.inject;

import android.app.Application;

import org.dbtools.android.domain.AndroidDatabase;
import org.dbtools.android.domain.config.DatabaseConfig;
import org.dbtools.android.domain.database.JdbcSqliteDatabaseWrapper;
import org.jdc.template.Analytics;
import org.jdc.template.TestFilesystem;
import org.jdc.template.model.database.DatabaseManager;

import java.io.File;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.AdditionalMatchers.or;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@Module
public class CommonTestModule {
    @Provides
    @Singleton
    Analytics provideAnalytics() {
        return new Analytics() {
            @Override
            public void send(Map<String, String> params) {
                System.out.println(params.toString());
            }
        };
    }

    // ========== ANDROID ==========
    @Provides
    @Singleton
    Application provideApplication() {
        Application application = mock(Application.class);

        when(application.getFilesDir()).thenReturn(TestFilesystem.INTERNAL_FILES_DIR);

        doAnswer(invocation -> {
            String type = invocation.getArgument(0);
            if (type != null) {
                return new File(TestFilesystem.EXTERNAL_FILES_DIR, type);
            } else {
                return TestFilesystem.EXTERNAL_FILES_DIR;
            }
        }).when(application).getExternalFilesDir(or(isNull(String.class), anyString()));

        return application;
    }

    @Provides
    @Singleton
    DatabaseManager provideDatabaseManager(DatabaseConfig databaseConfig) {
        DatabaseManager databaseManager = spy(new DatabaseManager(databaseConfig));

        // don't allow the database to be upgraded
        doNothing().when(databaseManager).onUpgrade(any(AndroidDatabase.class), anyInt(), anyInt());

        JdbcSqliteDatabaseWrapper.setEnableLogging(true);

        return databaseManager;
    }
}

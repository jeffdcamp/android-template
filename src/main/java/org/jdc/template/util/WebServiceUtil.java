package org.jdc.template.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;


@Singleton
public class WebServiceUtil {
    @Inject
    public WebServiceUtil() {
        // Dagger
    }

    public boolean saveResponseToFile(Response<ResponseBody> response, File outputFile) {
        Timber.d("Saving response [%s] to file [%s]...", response.raw().request().url().url().toString(), outputFile.getAbsolutePath());
        boolean success = false;
        try {
            FileUtils.copyInputStreamToFile(response.body().byteStream(), outputFile); // Closes all streams
            success = outputFile.exists();
        } catch (IOException e) {
            Timber.e(e, "Failed to save webservice stream to [%s]", outputFile.getName());

            if (outputFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                outputFile.delete(); // NOSONAR - just delete if we can but we don't need to check the return value
            }
        }
        return success;
    }
}

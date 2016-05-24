package org.jdc.template.model.webservice.colors;

import org.jdc.template.model.webservice.colors.dto.DtoColors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ColorService {
    String BASE_URL = "https://raw.githubusercontent.com";
    String SUB_URL = "/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/json/rest-test.json";
    String FULL_URL = BASE_URL + SUB_URL;

    @GET(SUB_URL)
    Call<DtoColors> colors();

    @GET
    Call<DtoColors> colorsByFullUrl(@Url String url);

    @Streaming
    @GET(SUB_URL)
    Call<ResponseBody> colorsToFile();
}

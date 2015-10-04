package org.jdc.template.webservice.websearch;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.Streaming;
import retrofit.http.Url;

public interface WebSearchService {
    String BASE_URL = "https://ajax.googleapis.com";

    @GET("/ajax/services/search/web?v=1.0")
    Call<DtoSearchResponse> search(@Query("q") String query);

    @GET
    Call<DtoSearchResponse> searchByFullUrl(@Url String url);

    @Streaming
    @GET("/ajax/services/search/web?v=1.0")
    Call<ResponseBody> searchToFile(@Query("q") String query);
}

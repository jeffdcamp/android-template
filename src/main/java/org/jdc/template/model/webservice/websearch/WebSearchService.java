package org.jdc.template.model.webservice.websearch;

import org.jdc.template.model.webservice.websearch.dto.DtoSearchResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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

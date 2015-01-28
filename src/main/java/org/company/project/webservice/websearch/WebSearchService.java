package org.company.project.webservice.websearch;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface WebSearchService {
    public static final String BASE_URL = "https://ajax.googleapis.com/ajax";

    @GET("/services/search/web?v=1.0")
    void search(@Query("q") String query, Callback<DtoSearchResponse> callback);
}

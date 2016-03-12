
package org.jdc.template.model.webservice.websearch

import okhttp3.ResponseBody
import org.jdc.template.model.webservice.websearch.dto.DtoSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

interface WebSearchService {

    @GET("/ajax/services/search/web?v=1.0")
    fun search(@Query("q") query: String): Call<DtoSearchResponse>

    @GET
    fun searchByFullUrl(@Url url: String): Call<DtoSearchResponse>

    @Streaming
    @GET("/ajax/services/search/web?v=1.0")
    fun searchToFile(@Query("q") query: String): Call<ResponseBody>

    companion object {
        val BASE_URL = "https://ajax.googleapis.com"
    }
}

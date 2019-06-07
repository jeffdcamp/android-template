package org.jdc.template.model.webservice.colors

import okhttp3.ResponseBody
import org.jdc.template.model.webservice.colors.dto.ColorsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ColorService {

    @GET("/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/json/rest-test.json")
    suspend fun colors(): Response<ColorsDto>

    @GET
    suspend fun colorsByFullUrl(@Url url: String): Response<ColorsDto>

    @Streaming
    @GET("/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/json/rest-test.json")
    suspend fun colorsToFile(): Response<ResponseBody>

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com"
        const val SUB_URL = "/jeffdcamp/android-template/33017aa38f59b3ff728a26c1ee350e58c8bb9647/src/test/json/rest-test.json"
        const val FULL_URL = BASE_URL + SUB_URL
    }
}
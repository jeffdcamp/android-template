package org.jdc.template.datasource.webservice.individuals

import org.jdc.template.datasource.webservice.individuals.dto.DtoIndividuals
import retrofit2.Call
import retrofit2.http.GET

interface IndividualService {

    @GET("/mobile/interview/directory")
    fun individuals(): Call<DtoIndividuals>

    companion object {
        const val BASE_URL = "https://ldscdn.org"
        const val SUB_URL = "/mobile/interview/directory"
        val FULL_URL = BASE_URL + SUB_URL
    }
}
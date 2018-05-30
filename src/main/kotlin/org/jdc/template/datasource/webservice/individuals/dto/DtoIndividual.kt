package org.jdc.template.datasource.webservice.individuals.dto;

import java.util.*

data class DtoIndividual(val id: Long,
                         val firstName: String,
                         val lastName: String,
                         val birthdate: String,
                         val profilePicture: String,
                         val forceSensitive: Boolean,
                         val affiliation: String)

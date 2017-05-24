package org.jdc.template.datasource.webservice.colors.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class DtoColor(val colorName: String, val hexValue: String)

package org.jdc.template.model.webservice.colors.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class DtoColor(val colorName: String, val hexValue: String)

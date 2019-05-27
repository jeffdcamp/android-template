package org.jdc.template.model.webservice.colors.dto

import kotlinx.serialization.Serializable

@Serializable
data class DtoColor(val colorName: String, val hexValue: String)

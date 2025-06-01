package org.jdc.template.shared.model.webservice.colors.dto

import kotlinx.serialization.Serializable

@Serializable
data class ColorDto(val colorName: String, val hexValue: String)

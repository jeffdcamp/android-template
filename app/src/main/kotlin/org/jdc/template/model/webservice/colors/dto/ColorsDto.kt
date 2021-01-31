package org.jdc.template.model.webservice.colors.dto

import kotlinx.serialization.Serializable

@Serializable
data class ColorsDto(val colors: List<ColorDto>)

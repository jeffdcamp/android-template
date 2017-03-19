package org.jdc.template.model.webservice.colors.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class DtoColor {
    var colorName: String? = null
    var hexValue: String? = null
}

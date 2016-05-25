package org.jdc.template.model.webservice.colors.dto

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
class DtoColor {
    @JsonField
    var colorName: String? = null
    @JsonField
    var hexValue: String? = null
}

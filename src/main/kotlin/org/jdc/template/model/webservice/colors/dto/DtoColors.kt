package org.jdc.template.model.webservice.colors.dto

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
class DtoColors {
    @JsonField
    var colors: List<DtoColor>? = null
}

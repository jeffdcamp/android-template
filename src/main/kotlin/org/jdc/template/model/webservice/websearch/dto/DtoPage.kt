package org.jdc.template.model.webservice.websearch.dto

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
class DtoPage {
    @JsonField
    var start: String? = null
    @JsonField
    var label: Int = 0
}

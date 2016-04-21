package org.jdc.template.model.webservice.websearch.dto

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
class DtoResponseData {
    @JsonField
    var results: List<DtoResult>? = null
    @JsonField
    var cursor: DtoCursor? = null
}

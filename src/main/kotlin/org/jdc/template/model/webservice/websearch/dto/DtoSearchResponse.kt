package org.jdc.template.model.webservice.websearch.dto

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
class DtoSearchResponse {
    @JsonField
    var responseData: DtoResponseData? = null
    @JsonField
    var responseStatus: Int = 0
}

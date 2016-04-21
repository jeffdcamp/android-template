package org.jdc.template.model.webservice.websearch.dto

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
class DtoResult {
    @JsonField
    var unescapedUrl: String? = null
    @JsonField
    var url: String? = null
    @JsonField
    var visibleUrl: String? = null
    @JsonField
    var cacheUrl: String? = null
    @JsonField
    var title: String? = null
    @JsonField
    var titleNoFormatting: String? = null
    @JsonField
    var content: String? = null
    @JsonField
    var curor: List<DtoCursor>? = null
}

package org.jdc.template.model.webservice.websearch.dto

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import com.bluelinelabs.logansquare.annotation.OnJsonParseComplete
import com.bluelinelabs.logansquare.annotation.OnPreJsonSerialize

@JsonObject
class DtoCursor {
    @JsonField
    var resultCount: String? = null
    @JsonField
    var pages: List<DtoPage>? = null
    @JsonField
    var estimatedResultCount: String? = null
    @JsonField
    var currentPageIndex: Int = 0
    @JsonField
    var moreResultsUrl: String? = null
    @JsonField
    var searchResultTime: String? = null
}

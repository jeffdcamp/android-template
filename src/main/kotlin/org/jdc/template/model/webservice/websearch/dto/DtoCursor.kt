package org.jdc.template.model.webservice.websearch.dto

class DtoCursor {
    var resultCount: String? = null
    var pages: List<DtoPage>? = null
    var estimatedResultCount: String? = null
    var currentPageIndex: Int = 0
    var moreResultsUrl: String? = null
    var searchResultTime: String? = null
}

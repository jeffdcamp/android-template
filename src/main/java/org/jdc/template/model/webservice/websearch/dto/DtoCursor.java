package org.jdc.template.model.webservice.websearch.dto;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class DtoCursor {
    @JsonField
    private String resultCount;
    @JsonField
    private List<DtoPage> pages;
    @JsonField
    private String estimatedResultCount;
    @JsonField
    private int currentPageIndex;
    @JsonField
    private String moreResultsUrl;
    @JsonField
    private String searchResultTime;

    public String getResultCount() {
        return resultCount;
    }

    public void setResultCount(String resultCount) {
        this.resultCount = resultCount;
    }

    public List<DtoPage> getPages() {
        return pages;
    }

    public void setPages(List<DtoPage> pages) {
        this.pages = pages;
    }

    public String getEstimatedResultCount() {
        return estimatedResultCount;
    }

    public void setEstimatedResultCount(String estimatedResultCount) {
        this.estimatedResultCount = estimatedResultCount;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public String getMoreResultsUrl() {
        return moreResultsUrl;
    }

    public void setMoreResultsUrl(String moreResultsUrl) {
        this.moreResultsUrl = moreResultsUrl;
    }

    public String getSearchResultTime() {
        return searchResultTime;
    }

    public void setSearchResultTime(String searchResultTime) {
        this.searchResultTime = searchResultTime;
    }
}

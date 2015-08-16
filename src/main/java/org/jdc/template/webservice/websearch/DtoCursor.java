package org.jdc.template.webservice.websearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoCursor {
    private String resultCount;
    private List<DtoPage> pages;
    private String estimatedResultCount;
    private int currentPageIndex;
    private String moreResultsUrl;
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

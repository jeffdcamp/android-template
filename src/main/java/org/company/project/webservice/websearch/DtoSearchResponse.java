package org.company.project.webservice.websearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoSearchResponse {
    private DtoResponseData responseData;
    private int responseStatus;

    public DtoResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(DtoResponseData responseData) {
        this.responseData = responseData;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
}

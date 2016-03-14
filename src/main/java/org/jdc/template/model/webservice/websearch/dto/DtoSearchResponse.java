package org.jdc.template.model.webservice.websearch.dto;

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

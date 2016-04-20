package org.jdc.template.model.webservice.websearch.dto;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

@JsonObject
public class DtoSearchResponse {
    @JsonField
    private DtoResponseData responseData;
    @JsonField
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

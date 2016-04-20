package org.jdc.template.model.webservice.websearch.dto;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class DtoResponseData {
    @JsonField
    private List<DtoResult> results;
    @JsonField
    private DtoCursor cursor;

    public List<DtoResult> getResults() {
        return results;
    }

    public void setResults(List<DtoResult> results) {
        this.results = results;
    }

    public DtoCursor getCursor() {
        return cursor;
    }

    public void setCursor(DtoCursor cursor) {
        this.cursor = cursor;
    }
}

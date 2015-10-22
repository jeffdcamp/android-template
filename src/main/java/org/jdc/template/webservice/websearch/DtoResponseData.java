package org.jdc.template.webservice.websearch;

import java.util.List;

public class DtoResponseData {
    private List<DtoResult> results;
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

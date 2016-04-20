package org.jdc.template.model.webservice.websearch.dto;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class DtoResult {
    @JsonField
    private String unescapedUrl;
    @JsonField
    private String url;
    @JsonField
    private String visibleUrl;
    @JsonField
    private String cacheUrl;
    @JsonField
    private String title;
    @JsonField
    private String titleNoFormatting;
    @JsonField
    private String content;
    @JsonField
    private List<DtoCursor> curor;

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public void setUnescapedUrl(String unescapedUrl) {
        this.unescapedUrl = unescapedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public void setVisibleUrl(String visibleUrl) {
        this.visibleUrl = visibleUrl;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleNoFormatting() {
        return titleNoFormatting;
    }

    public void setTitleNoFormatting(String titleNoFormatting) {
        this.titleNoFormatting = titleNoFormatting;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<DtoCursor> getCuror() {
        return curor;
    }

    public void setCuror(List<DtoCursor> curor) {
        this.curor = curor;
    }
}

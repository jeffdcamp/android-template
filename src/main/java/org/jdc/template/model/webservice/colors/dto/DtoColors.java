package org.jdc.template.model.webservice.colors.dto;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.List;

@JsonObject
public class DtoColors {
    @JsonField
    private List<DtoColor> colors;

    public List<DtoColor> getColors() {
        return colors;
    }

    public void setColors(List<DtoColor> colors) {
        this.colors = colors;
    }
}

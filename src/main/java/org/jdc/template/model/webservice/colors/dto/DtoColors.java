package org.jdc.template.model.webservice.colors.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoColors {
    private List<DtoColor> colors;

    public List<DtoColor> getColors() {
        return colors;
    }

    public void setColors(List<DtoColor> colors) {
        this.colors = colors;
    }
}

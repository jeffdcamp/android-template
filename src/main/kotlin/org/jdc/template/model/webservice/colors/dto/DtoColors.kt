package org.jdc.template.model.webservice.colors.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class DtoColors(val colors: List<DtoColor>)

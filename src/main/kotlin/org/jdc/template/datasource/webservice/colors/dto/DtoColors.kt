package org.jdc.template.datasource.webservice.colors.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class DtoColors(val colors: List<DtoColor>)

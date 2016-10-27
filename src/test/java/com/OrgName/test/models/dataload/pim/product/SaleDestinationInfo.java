package com.salmon.test.models.dataload.pim.product;

import lombok.Data;


@Data
public class SaleDestinationInfo {
    private String destinationCountry;
    private Boolean sellable;
    // private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}

package com.salmon.test.models.dataload.pim.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CommercialBanningPimModel {
    @JsonProperty("GTIN")
    private String gTIN;
    private String brandChannel;
    private String timestamp;
    private List<SaleDestinationInfo> saleDestinationInfo;
}

package com.salmon.test.models.dataload.pim.price;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Prices {
    private String fullPrice;
    private String salePrice;
    private String validFrom;
    private String validTo;
    private String markdownCode;
    private String markdownPercentage;
}

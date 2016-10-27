package com.salmon.test.models.dataload.wcs.price;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductPriceWcsModel {
    String partNumber;
    String price;
    String currencyCode;
    String priceListName;
    String precedence;
    String startDate;
    String endDate;
    String delete;
    String identifier;
    String markdownCode;
    String markdownPercentage;
    String catalogIdentifier;

}


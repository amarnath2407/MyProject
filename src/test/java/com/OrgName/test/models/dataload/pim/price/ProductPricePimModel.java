package com.salmon.test.models.dataload.pim.price;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductPricePimModel {
    private String gtin;
    private String brandChannel;
    private String country;
    private String currency;
    private String timestamp;
    private List<Prices> prices;
}


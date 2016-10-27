package com.salmon.test.models.dataload.pim.product;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductPimModel {
    String catalogIdentifier;
    String productType;
    String manufacturer;
    String sequence;
    String modelFabricBrandCode;
    String yooxCode8;
    String nappID;
    String yooxCode10;
    String gtin;
    String ean;
    String delete;
    String modelId;
    String variantId;
    String extraField;
}


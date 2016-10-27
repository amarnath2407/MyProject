package com.salmon.test.models.dataload.pim.product;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductCategoryPimModel {
    String gtin;
    String catalogIdentifier;
    String sequence;
    String delete;
    String parentGroupIdentifier;
    String productType;
    String modelId;
    String variantId;
    String extraField;
}

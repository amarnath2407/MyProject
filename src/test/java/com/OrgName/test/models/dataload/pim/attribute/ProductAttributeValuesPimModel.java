package com.salmon.test.models.dataload.pim.attribute;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductAttributeValuesPimModel {
    String gtin;
    String catalogIdentifier;
    String attributeValueIdentifier;
    String attributeIdentifier;
    String usage;
    String sequence;
    String delete;
    String productType;
    String modelId;
    String variantId;
    String extraField;
}

package com.salmon.test.models.dataload.pim.product;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductDescriptionPimModel {
    String gtin;
    String catalogIdentifier;
    String type;
    String language;
    String extendedName;
    String editorialDescription;
    String detailsAndCare;
    String sizeAndFit;
    String longDescription;
    String delete;
    String modelId;
    String variantId;
    String extraField;
}

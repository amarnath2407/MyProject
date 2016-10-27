package com.salmon.test.models.dataload.wcs.attribute;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Created by veeranank on 13/09/2016.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductAttributeValuesWcsModel {
    String partNumber;
    String parentStoreIdentifier;
    String attributeValueIdentifier;
    String attributeIdentifier;
    String usage;
    String sequence;
    String delete;
}

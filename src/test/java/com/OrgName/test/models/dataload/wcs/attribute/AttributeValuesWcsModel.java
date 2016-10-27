package com.salmon.test.models.dataload.wcs.attribute;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AttributeValuesWcsModel {
    String parentStoreIdentifier;
    String attributeValueIdentifier;
    String attributeIdentifier;
    String field1;
    String field2;
    String field3;
    String languageId;
    String value;
    String sequence;
    String image1;
    String image2;
    String attributeValueField1;
    String attributeValueField2;
    String attributeValueField3;
    String valUsage;
    String delete;
}

package com.salmon.test.models.dataload.pim.attribute;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AttributeValuesPimModel {
    String catalogIdentifier;
    String attributeValueIdentifier;
    String attributeIdentifier;
    String language;
    String value;
    String sequence;
    String delete;
    String extraField;
}

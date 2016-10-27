package com.salmon.test.models.dataload.pim.attribute;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AttributePimModel {
    String catalogIdentifier;
    String attributeIdentifier;
    String language;
    String name;
    String attrUsage;
    String delete;
    String extraField;
}

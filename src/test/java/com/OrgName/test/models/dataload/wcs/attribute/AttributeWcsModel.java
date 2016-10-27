package com.salmon.test.models.dataload.wcs.attribute;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Created by veeranank on 09/09/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AttributeWcsModel {
    String parentStoreIdentifier;
    String attributeIdentifier;
    String field1;
    String field2;
    String field3;
    String languageId;
    String name;
    String description;
    String description2;
    String attributeValueField1;
    String noteInfo;
    String attrUsage;
    String delete;
    String attributeType;
}

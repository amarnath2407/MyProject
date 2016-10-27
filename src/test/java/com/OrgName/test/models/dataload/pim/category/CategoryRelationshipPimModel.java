package com.salmon.test.models.dataload.pim.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CategoryRelationshipPimModel {
    String catalogIdentifier;
    String delete;
    String groupIdentifier;
    String parentGroupIdentifier;
    String sequence;
    String extraField;
}

package com.salmon.test.models.dataload.wcs.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CategoryRelationshipWcsModel {
    String groupIdentifier;
    String parentGroupIdentifier;
    String parentStoreIdentifier;
    String catalogIdentifier;
    String sequence;
    String delete;
}

package com.salmon.test.models.dataload.wcs.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CategoryWcsModel {
    String groupIdentifier;
    String parentStoreIdentifier;
    String catalogIdentifier;
    String topGroup;
    String sequence;
    String field1;
    String field2;
    String delete;
}

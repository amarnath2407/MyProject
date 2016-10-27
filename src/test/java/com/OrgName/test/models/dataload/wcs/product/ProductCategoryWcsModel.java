package com.salmon.test.models.dataload.wcs.product;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductCategoryWcsModel {
    String partNumber;
    String partNumberParentStoreIdentifier;
    String sequence;
    String delete;
    String parentGroupIdentifier;
    String parentStoreIdentifier;
}

package com.salmon.test.models.dataload.wcs.product;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductWcsModel {
    String parentStoreIdentifier;
    String partNumber;
    String parentPartNumber;
    String parentColourPartNumber;
    String type;
    String sequence;
    String manufacturerPartNumber;
    String manufacturer;
    String buyable;
    String startDate;
    String endDate;
    String field1;
    String field2;
    String field3;
    String field4;
    String delete;
    String catEntTypeId;
    String catEntryParentId;
    String catEntryChildId;
    String catRelTypeId;
}

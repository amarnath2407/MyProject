package com.salmon.test.models.dataload.wcs.product;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CommercialBanningWcsModel {
    String partNumber;
    String parentStoreIdentifier;
    String attributeValueIdentifier;
    String attributeIdentifier;
    String usage;
    String sequence;
    String delete;
}

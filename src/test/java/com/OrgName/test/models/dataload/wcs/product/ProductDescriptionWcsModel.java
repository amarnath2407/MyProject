package com.salmon.test.models.dataload.wcs.product;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProductDescriptionWcsModel {
    String languageId;
    String partNumber;
    String name;
    String extendedName;
    String editorialDescription;
    String detailsAndCare;
    String buyersDescription;
    String shortDescription;
    String sizeAndFit;
    String longDescription;
    String auxDescription1;
    String auxDescription2;
    String published;
    String delete;
    String parentStoreIdentifier;
}

package com.salmon.test.models.api.searchandbrowse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Created by Developer on 14/10/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogEntryView {

    String partNumber;
    String mfPartNumber;
    String field4;
    String extendedName;
    String storeID;
}

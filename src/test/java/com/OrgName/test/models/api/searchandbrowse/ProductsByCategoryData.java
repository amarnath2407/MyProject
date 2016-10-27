package com.salmon.test.models.api.searchandbrowse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by Developer on 14/10/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductsByCategoryData {

    String recordSetTotal;
    String resourceId;
    String recordSetCount;
    List<CatalogEntryView> catalogEntryView;

}

package com.salmon.test.models.api.searchandbrowse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by john elsen  on 04/10/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogGroup {
    String recordSetTotal;
    String resourceId;
    String recordSetCount;
    String recordSetComplete;
    String recordSetStartNumber;
    String resourceName;
    List<CatalogGroupView> catalogGroupView;
}

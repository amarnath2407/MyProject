package com.salmon.test.models.dataload.wcs.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CategoryDescriptionWcsModel {
    String groupIdentifier;
    String catalogIdentifier;
    String languageId;
    String name;
    String shortDescription;
    String longDescription;
    String published;
    String delete;
}

package com.salmon.test.models.dataload.pim.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class CategoryDescriptionPimModel {
    String groupIdentifier;
    String catalogIdentifier;
    String language;
    String name;
    String delete;
    String extraField;
}


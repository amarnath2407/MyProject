package com.salmon.test.models.api.loginidentity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;


// Container model for Item Model
@FieldDefaults(level = AccessLevel.PUBLIC)
@Builder // Reduces massive vanilla code of Builder implementation Java
@Data

public class Preference {
    public String storeId;
    public String value;
}


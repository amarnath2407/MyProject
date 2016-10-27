package com.salmon.test.models.api.storelocator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Created by akepativ on 03/08/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class Language {

    String languageId;
    String country;
    String localeName;
    String language;
}

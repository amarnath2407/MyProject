package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by akepativ on 13/07/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute {

    String identifier;
    String type;
    String name;
    List<AttributeValues> values;
}

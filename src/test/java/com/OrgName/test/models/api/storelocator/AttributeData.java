package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.util.List;

/**
 * Created by veeranank on 22/08/2016.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)

public class AttributeData {
    String status;
    List<Attribute> data;
}

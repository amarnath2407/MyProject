package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salmon.test.models.api.errors.ErrorType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by Developer on 05/09/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalStoreLocatorData {

    String status;
    StorelocatorItem data;
    List<ErrorType> errors;
}

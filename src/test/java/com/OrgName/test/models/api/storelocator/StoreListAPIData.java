package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by akepativ on 08/08/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreListAPIData {

    String recordSetTotal;
    String recordSetStartNumber;
    String recordSetComplete;
    String recordSetCount;
    List<StoreAPIData> PhysicalStore;
}

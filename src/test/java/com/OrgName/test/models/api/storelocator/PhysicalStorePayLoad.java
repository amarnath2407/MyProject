package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by Developer on 07/09/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhysicalStorePayLoad {

    String identifier;
    String storeName;
    String storeNumber;
    boolean active;
    Address address;
    GeoData spatialData;
    List<ContactDetails> contacts;
    List<StoreOpeningHours> openingTimes;
    List<Attribute> attributes;
}

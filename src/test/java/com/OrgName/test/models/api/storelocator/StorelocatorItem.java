package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by akepativ on 26/07/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StorelocatorItem {

    String identifier;
    Boolean active;
    String storeName;
    String storeNumber;
    GeoDistance geoDistance;
    Address address;
    GeoData spatialData;
    List<ContactDetails> contacts;
    List<StoreOpeningHours> openingTimes;
    List<Attribute> attributes;
}

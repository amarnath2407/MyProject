package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Created by akepativ on 01/08/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    String line1;
    String line2;
    String line3;
    String city;
    String state;
    String countryCode;
    String countryName;
    String postCode;
    String regionCode;
    String regionName;
}

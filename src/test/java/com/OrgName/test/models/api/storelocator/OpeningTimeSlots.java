package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * Created by Developer on 18/08/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpeningTimeSlots {

    int slot;
    String openTime;
    String closeTime;
    String openTimeHour;
    String openTimeMinute;
    String closeTimeHour;
    String closeTimeMinute;
}

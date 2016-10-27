package com.salmon.test.models.api.storelocator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salmon.test.models.api.errors.ErrorType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by akepativ on 27/07/2016.
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactsTypes {

    String status;
    List<ContactsType> data;
    List<ErrorType> errors;
}

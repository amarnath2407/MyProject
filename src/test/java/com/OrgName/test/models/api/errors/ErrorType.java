package com.salmon.test.models.api.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by Developer on 22/08/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorType {

    String errorMessage;
    List<String> errorParameters;
    String errorCode;
    String errorKey;
}

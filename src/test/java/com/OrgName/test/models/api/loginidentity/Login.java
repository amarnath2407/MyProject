package com.salmon.test.models.api.loginidentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.salmon.test.models.api.errors.ErrorType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * Created by Developer on 07/10/2016.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Login {
    String WCToken;
    String userId;
    String WCTrustedToken;
    String personalizationID;
    List<ErrorType> errors;
}

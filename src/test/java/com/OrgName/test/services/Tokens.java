package com.salmon.test.services;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PROTECTED)
@Data
public class Tokens {
    public static String WCToken;
    public static String WCTrustedToken;
}


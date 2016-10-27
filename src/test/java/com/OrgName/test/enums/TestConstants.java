package com.salmon.test.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
* Define Constants.
* Create a new enum Class for defining new constants
*/


@AllArgsConstructor
public enum TestConstants {

    SALMON_TEST_FRAMEWORK("salmon test framework"),
    TEST_COUNT("test count"),
    CONTENT_TYPE_JSON("application/json"),
    STORE_LANG_EN_GB_CODE(44),
    STATUS_CODE_GET_OK("200"),
    STATUS_CODE_OK("200"),
    STATUS_CODE_CREATED("201"),
    STATUS_CODE_FORBIDDEN("403"),
    STATUS_CODE_NOT_FOUND("404"),
    STATUS_CODE_BAD_REQUEST("400"),
    HTTP_PORT(80),
    HTTPS_PORT(9162);

    private int intValue;
    private String stringValue;

    TestConstants(int value) {
        this.intValue = value;
    }
    TestConstants(String svalue) {
        this.stringValue = svalue;
    }
    public int getIntValue(){
        return intValue;
    }
    public String getStringValue(){
        return stringValue;
    }

    @Getter
    String cssClass;
}

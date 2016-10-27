package com.salmon.test.models.api.loginidentity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

// Container model for Item Model

@FieldDefaults(level = AccessLevel.PUBLIC)
@Builder // Reduces massive vanilla code of Builder implementation Java
@Data
 public class LoginPayload {
    public String logonId;
    public String logonPassword;
    public String logonPasswordVerify;
    public String firstName;
    public String lastName;
    public List<String> addressLine;
    public String city;
    public String state;
    public String country;
    public String zipCode;
    public String gender;
    public String preferredLanguage;
    public String bestCallingTime;
    public String personTitle;
    public String email1;
    public String email2;
    public String phone1;
    public String mobilePhone1;
    public String mobilePhone1Country;
    public List<Preference> receiveEmailPreference;
    public String receiveSMSNotification;
    public List<Preference> receiveSMSPreference;
    public String profileType;
    public String challengeAnswer;
}





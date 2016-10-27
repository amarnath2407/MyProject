package com.salmon.test.services.loginidentity;

import com.google.gson.Gson;
import com.jayway.restassured.response.Response;
import com.salmon.test.enums.ServiceEndPoints;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.ApiHelper;
import com.salmon.test.framework.helpers.Props;
import com.salmon.test.models.api.loginidentity.Login;
import com.salmon.test.models.api.loginidentity.LoginPayload;
import com.salmon.test.models.api.loginidentity.Preference;
import com.salmon.test.services.Tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.salmon.test.enums.PermittedCharacters.ALPHABETS;
import static com.salmon.test.enums.PermittedCharacters.NUMERIC;
import static com.salmon.test.framework.helpers.utils.RandomGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

public class login extends ApiHelper {

    private static final String PATH = "/person/";

    // creates the payload for register user
    public static LoginPayload personData( ) {
        // build address
        List<String> address = new ArrayList<>();
        address.add(0,"address0"+random(4,ALPHABETS));
        address.add(1,"address1"+random(4,ALPHABETS));
        // build email preference
        List<Preference> emailPreferenceList = new ArrayList<>();
        Preference emailPreference=Preference.builder().value(Boolean.toString(getRandomBoolean())).storeId("10151").build();
        emailPreferenceList.add(emailPreference);
        // build sms preference
        List<Preference> smsPreferenceList = new ArrayList<>();
        Preference smsPreference=Preference.builder().value(Boolean.toString(getRandomBoolean())).storeId("10151").build();
        smsPreferenceList.add(smsPreference);
        Random rand = new Random();
        // set random data for the payload
        return LoginPayload.builder().
                logonId(randomEmailAddress(8)).
                logonPassword("pa55word").
                logonPasswordVerify("pa55word").
                firstName("first"+random(6,ALPHABETS)).
                lastName("last"+random(7,ALPHABETS)).
                addressLine(address).
                city("city"+random(4,ALPHABETS)).
                state("state"+random(4,ALPHABETS)).
                country(Props.getMessage("prop.countryAbbr").split(",")[rand.nextInt(Props.getMessage("prop.countryAbbr").split(",").length)]).
                zipCode(random(2,ALPHABETS)+random(2,NUMERIC)+" "+random(1,NUMERIC)+random(2,ALPHABETS)).
                gender(randomGenderFullText()).
                preferredLanguage(Props.getMessage("prop.languages").split(",")[rand.nextInt(Props.getMessage("prop.languages").split(",").length)]).
                bestCallingTime("Evening").
                personTitle("Mr.").
                email1(randomEmailAddress(8)).
                email2(randomEmailAddress(9)).
                phone1("0"+random(10,NUMERIC)).
                mobilePhone1("07"+random(9,NUMERIC)).
                mobilePhone1Country(Props.getMessage("prop.countries").split(",")[rand.nextInt(Props.getMessage("prop.countries").split(",").length)]).
                receiveEmailPreference(emailPreferenceList).
                receiveSMSNotification(Boolean.toString(getRandomBoolean())).
                receiveSMSPreference(smsPreferenceList).
                profileType("Consumer").
                challengeAnswer("-").build();
    }

    public static LoginPayload existingUserLoginData( String userName, String password ) {
        return LoginPayload.builder().
                logonId(userName).
                logonPassword(password).build();
    }

    public  static Response registerUser( LoginPayload thisLoginPayload, String storeIdentifier) {
        // make sure last login tokens don't remain
        Tokens.WCToken = null;
        Tokens.WCTrustedToken = null;
        String payLoad = gson().toJson(thisLoginPayload);
        System.out.println("payload="+payLoad);
        String url = ServiceEndPoints.PERSON.getUrl().replace("{storeId}",storeIdentifier);
        ChangeProtocol("https");
       Response response =givenConfig().
               body(payLoad).
               port(TestConstants.HTTPS_PORT.getIntValue()).
               log().all().
               post(url);
        Tokens.WCToken = response.body().jsonPath().get("WCToken");
        Tokens.WCTrustedToken = response.body().jsonPath().get("WCTrustedToken");
        assertThat(Tokens.WCToken != null && Tokens.WCTrustedToken  != null);
        return response;
    }

    public  static Response loginUser( String storeIdentifier, LoginPayload thisLoginPayload) {
        String url = ServiceEndPoints.LOGIN_IDENTITY.getUrl().replace("{storeId}",storeIdentifier);
        Login login;
        Gson gson = new Gson();
        // make sure last login tokens don't remain
        Tokens.WCToken = null;
        Tokens.WCTrustedToken = null;
        ChangeProtocol("https");
        String payLoad = gson().toJson(thisLoginPayload);
        Response response =givenConfig().
                body(payLoad).log().all().port(TestConstants.HTTPS_PORT.getIntValue()).
                post(url);
        Tokens.WCToken = response.body().jsonPath().get("WCToken");
        Tokens.WCTrustedToken = response.body().jsonPath().get("WCTrustedToken");
        login = gson.fromJson(response.asString(), Login.class);
        if(login.getErrors() != null) {
            assertThat(Tokens.WCToken).as(login.getErrors().get(0).getErrorMessage()).isNotNull();
        }
        return response;
    }

 }

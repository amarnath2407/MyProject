package com.salmon.test.step_definitions.api.loginhelper;

import com.jayway.restassured.response.Response;
import com.salmon.test.enums.Store;
import com.salmon.test.enums.UserType;
import com.salmon.test.framework.helpers.UrlBuilder;
import com.salmon.test.models.api.loginidentity.LoginPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.salmon.test.services.guestidentity.login.guestIdentity;
import static com.salmon.test.services.loginidentity.login.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.fail;

public class LoginHelper {

    private static final Logger log = LoggerFactory.getLogger(LoginHelper.class);

    public Response login(UserType userType, Store storeIdentifier) {
        return  login(userType.name(), storeIdentifier.name());
    }

    public Response login(String userType, String storeIdentifier) {
        LoginPayload testLoginPayload;
        String userId;
        Response response;
        try {
            switch (userType) {
                case "guest":
                    response = guestIdentity(storeIdentifier);
                    userId = response.body().jsonPath().get("userId");
                    assertThat(userId != null);
                    break;
                case "admin":
                    testLoginPayload = existingUserLoginData(UrlBuilder.readValueFromConfig("wcsadmin.username"), UrlBuilder.readValueFromConfig("wcsadmin.password"));
                    response = loginUser(storeIdentifier, testLoginPayload);
                    userId = response.body().jsonPath().get("userId");
                    break;
                default:
                    testLoginPayload = personData();
                    response = registerUser(testLoginPayload, storeIdentifier);
                    userId = response.body().jsonPath().get("userId");
                    break;
            }
            return response;
        }catch(Exception e){
            String message = "Exception while getting tokens from identity service:"+e;
            log.error(message);
            fail(message);
        }
        return null;
    }
}

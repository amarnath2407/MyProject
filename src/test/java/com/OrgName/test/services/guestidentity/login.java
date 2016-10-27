package com.salmon.test.services.guestidentity;

import com.jayway.restassured.response.Response;
import com.salmon.test.enums.ServiceEndPoints;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.ApiHelper;
import com.salmon.test.services.Tokens;

public class login extends ApiHelper {

    public  static Response guestIdentity(String storeIdentifier) {

        Tokens.WCToken = null;
        Tokens.WCTrustedToken = null;
        String url = ServiceEndPoints.GUEST_IDENTITY.getUrl().replace("{storeId}",storeIdentifier);
        ChangeProtocol("https");
        Response response = givenConfig().log().all().port(TestConstants.HTTPS_PORT.getIntValue()).post(url);
        System.out.println("\n guest response="+response.asString());
        Tokens.WCToken = response.body().jsonPath().get("WCToken");
        Tokens.WCTrustedToken = response.body().jsonPath().get("WCTrustedToken");
        return response;
    }
}

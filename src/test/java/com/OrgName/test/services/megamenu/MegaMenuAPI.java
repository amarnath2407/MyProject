package com.salmon.test.services.megamenu;

import com.jayway.restassured.response.Response;
import com.salmon.test.enums.ServiceEndPoints;
import com.salmon.test.framework.helpers.ApiHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by Developer on 11/10/2016.
 */
public class MegaMenuAPI extends ApiHelper {

    private static Logger logger = LoggerFactory.getLogger(MegaMenuAPI.class);

    public static Response getMegaMenu(String params) {
        Response response = null;
        try {
            String[] megaMenuParams = params.split("_");
            String url = ServiceEndPoints.GET_MEGAMENU.getUrl();
            response = givenCoreMediaConfig()
                    .log().all()
                    .when()
                    .get(url, megaMenuParams[0], megaMenuParams[1], megaMenuParams[2], megaMenuParams[3], megaMenuParams[4]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return response;
    }

}

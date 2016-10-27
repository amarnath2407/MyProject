package com.salmon.test.services.cart;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.salmon.test.enums.Store;
import com.salmon.test.enums.TestConstants;
import com.salmon.test.framework.helpers.ApiHelper;
import com.salmon.test.models.api.cart.request.AddToCartRequestModel;
import com.salmon.test.models.api.cart.request.OrderItemRequestModel;
import com.salmon.test.services.Tokens;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CartService extends ApiHelper {
    private static final Logger log = LoggerFactory.getLogger(CartService.class);

    private static final String PATH = "/cart/";
    private static final String BASE_PATH = "wcs/resources/store/";
    private static final String GET_CART_URI = "{storeId}/cart/@self";

    public static Response getCart() {
        return getRS().get(GET_CART_URI);
    }

    public static Response addToBasket(Optional<List<OrderItemRequestModel>> orderItems, Store storeIdentifier) {
        orderItems.orElseThrow(() -> new RuntimeException("AddToCartRequestModel items should not be empty"));
        return getRS().body(gson().toJson(AddToCartRequestModel.builder().orderItem(orderItems.get()).build())).post(storeIdentifier + PATH);
    }

    public static Response addToBasket(String payload, String uri) {
        log.info("ADD TO CART URI IS --> " + uri);
        return  getRS().body(payload).post(uri);
    }

    private static RequestSpecification getRS() {
        ChangeProtocol("https");
        return givenConfig()
                .basePath(BASE_PATH)
                .port(TestConstants.HTTPS_PORT.getIntValue())
                .header("WCToken", Tokens.WCToken)
                .header("WCTrustedToken", Tokens.WCTrustedToken)
                .log().all();
    }
}

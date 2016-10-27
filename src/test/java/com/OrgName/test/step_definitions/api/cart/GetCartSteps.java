package com.salmon.test.step_definitions.api.cart;
import com.jayway.restassured.response.Response;
import com.salmon.test.enums.Store;
import com.salmon.test.services.cart.CartService;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class GetCartSteps {
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(AddToBasketSteps.class);

    private AddToBasketSteps addToBasketSteps;
    private CartService cartService;
    private Supplier<Response> getCartDetails = CartService::getCart;
    private Store currentStore;
    Response getCartResponseModel;

    public GetCartSteps(AddToBasketSteps addToBasketSteps) {
       this.addToBasketSteps = addToBasketSteps;
       currentStore = Store.valueOf(Optional.ofNullable(System.getProperty("currentStore"))
                .orElse(Store.Moncler_GB.name()));
       LOG.info(this.getClass().getName() + "is initialized for the store --> " + this.currentStore);
    }

    @When("^cart details are requested for this user$")
    public void cartDetailsAreRequestedForThisUser() throws Throwable {
        getCartResponseModel = getCartDetails.get();
    }

    @Then("^the GET cart API response should have (\\d+) total number of items in the cart$")
    public void theGETCartAPIResponseShouldHaveTotalNumberOfItemsInTheCart(int totalItems) throws Throwable {

    }

    @Then("^the GET cart API response should include all product details$")
    public void theGETCartAPIResponseShouldIncludeAllProductDetails() throws Throwable {
    }
}

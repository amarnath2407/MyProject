package com.salmon.test.models.api.cart.response;


import com.salmon.test.models.api.searchandbrowse.CatalogEntryView;

import java.util.List;

public class GetCartResponseModel {
    List<AddToCartResponseModel.OrderItem> orderItem;
    String orderId;
    private double x_totalOrderQuantity;

    public static class OrderItem {
        public String orderItemId;
        public CatalogEntryView catalogEntryView;
    }
}
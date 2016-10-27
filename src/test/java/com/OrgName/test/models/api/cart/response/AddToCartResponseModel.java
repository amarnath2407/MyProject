package com.salmon.test.models.api.cart.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AddToCartResponseModel {
    List<OrderItem> orderItem;
    String orderId;

    public static class OrderItem {
        public String orderItemId;
    }
}


package com.salmon.test.models.api.cart.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class AddToCartRequestModel {
    String channelId;
    List<OrderItemRequestModel> orderItem;
}





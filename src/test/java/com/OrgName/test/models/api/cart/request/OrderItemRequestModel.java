package com.salmon.test.models.api.cart.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class OrderItemRequestModel {
    String productId;
    String partNumber;
    String quantity;
}

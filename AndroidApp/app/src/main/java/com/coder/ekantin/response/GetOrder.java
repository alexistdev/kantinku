package com.coder.ekantin.response;

import com.coder.ekantin.model.OrderModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrder {
    @SerializedName("data")
    List<OrderModel> daftarOrder;

    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private Boolean status;

    public GetOrder(List<OrderModel> daftarOrder, String message, Boolean status) {
        this.daftarOrder = daftarOrder;
        this.message = message;
        this.status = status;
    }

    public List<OrderModel> getDaftarOrder() {
        return daftarOrder;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}

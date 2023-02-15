package com.coder.ekantin.response;

import com.coder.ekantin.model.MenuModelMerchant;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMenuMerchant {
    @SerializedName("data")
    List<MenuModelMerchant> daftarMenuMerchant;

    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private Boolean status;

    public GetMenuMerchant(List<MenuModelMerchant> daftarMenuMerchant, String message, Boolean status) {
        this.daftarMenuMerchant = daftarMenuMerchant;
        this.message = message;
        this.status = status;
    }

    public List<MenuModelMerchant> getDaftarMenuMerchant() {
        return daftarMenuMerchant;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}

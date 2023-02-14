package com.coder.ekantin.response;

import com.coder.ekantin.model.MenuModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetKeranjang {
    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private Boolean status;

    @SerializedName("total")
    final private String total;

    @SerializedName("data")
    List<MenuModel> daftarMenu;

    public GetKeranjang(String message, Boolean status, String total, List<MenuModel> daftarMenu) {
        this.message = message;
        this.status = status;
        this.total = total;
        this.daftarMenu = daftarMenu;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getTotal() {
        return total;
    }

    public List<MenuModel> getDaftarMenu() {
        return daftarMenu;
    }
}

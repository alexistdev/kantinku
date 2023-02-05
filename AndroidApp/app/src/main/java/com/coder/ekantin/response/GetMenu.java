package com.coder.ekantin.response;

import com.coder.ekantin.model.MenuModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetMenu {
    @SerializedName("data")
    List<MenuModel> daftarMenu;

    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private Boolean status;

    public GetMenu(List<MenuModel> daftarMenu, String message, Boolean status) {
        this.daftarMenu = daftarMenu;
        this.message = message;
        this.status = status;
    }

    public List<MenuModel> getDaftarMenu() {
        return daftarMenu;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}

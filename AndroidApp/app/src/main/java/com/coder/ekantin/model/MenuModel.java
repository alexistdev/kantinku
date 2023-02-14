package com.coder.ekantin.model;

import com.google.gson.annotations.SerializedName;

public class MenuModel {
    @SerializedName("id")
    private final String idMenu;

    @SerializedName("nama")
    private final String namaMenu;

    @SerializedName("harga")
    private final String hargaMenu;

    public MenuModel(String idMenu, String namaMenu, String hargaMenu) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.hargaMenu = hargaMenu;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public String getHargaMenu() {
        return hargaMenu;
    }

    @Override
    public String toString() {
        return "MenuModel{" +
                "idMenu='" + idMenu + '\'' +
                ", namaMenu='" + namaMenu + '\'' +
                ", hargaMenu='" + hargaMenu + '\'' +
                '}';
    }
}

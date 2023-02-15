package com.coder.ekantin.model;

import com.google.gson.annotations.SerializedName;

public class MenuModelMerchant {
    @SerializedName("id")
    private final String idMenu;

    @SerializedName("nama")
    private final String nama;

    @SerializedName("harga")
    private final String harga;

    @SerializedName("tipe")
    private final String tipe;

    public MenuModelMerchant(String idMenu, String nama, String harga, String tipe) {
        this.idMenu = idMenu;
        this.nama = nama;
        this.harga = harga;
        this.tipe = tipe;
    }

    public String getIdMenu() {
        return idMenu;
    }

    public String getNama() {
        return nama;
    }

    public String getHarga() {
        return harga;
    }

    public String getTipe() {
        return tipe;
    }
}

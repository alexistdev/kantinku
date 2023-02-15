package com.coder.ekantin.model;

import com.coder.ekantin.api.NamaMerchantDeserializer;
import com.coder.ekantin.api.UsernameDeserializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class MenuModel {

    @SerializedName("id")
    private final String idMenu;

    @SerializedName("nama")
    private final String namaMenu;

    @SerializedName("harga")
    private final String hargaMenu;

    @SerializedName("merchant")
    @JsonAdapter(UsernameDeserializer.class)
    private final String namaMerchant;

    public MenuModel(String idMenu, String namaMenu, String hargaMenu, String namaMerchant) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.hargaMenu = hargaMenu;
        this.namaMerchant = namaMerchant;
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

    public String getNamaMerchant() {
        return namaMerchant;
    }
}

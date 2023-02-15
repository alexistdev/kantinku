package com.coder.ekantin.model;

import com.coder.ekantin.api.NamaMerchantDeserializer;
import com.coder.ekantin.api.UsernameDeserializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

public class DetailTransaksiModel {

    @SerializedName("jumlah")
    private final String harga;

    @SerializedName("menu")
    @JsonAdapter(NamaMerchantDeserializer.class)
    private final String namaMenu;

    public DetailTransaksiModel(String harga, String namaMenu) {
        this.harga = harga;
        this.namaMenu = namaMenu;
    }

    public String getHarga() {
        return harga;
    }

    public String getNamaMenu() {
        return namaMenu;
    }
}

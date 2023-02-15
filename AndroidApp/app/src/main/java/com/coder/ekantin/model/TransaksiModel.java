package com.coder.ekantin.model;

import com.google.gson.annotations.SerializedName;

public class TransaksiModel {
    @SerializedName("id")
    private final String idTransaksi;

    @SerializedName("total")
    private final String totalTransaksi;

    @SerializedName("statusTransaksi")
    private final String statusTransaksi;

    public TransaksiModel(String idTransaksi, String totalTransaksi, String statusTransaksi) {
        this.idTransaksi = idTransaksi;
        this.totalTransaksi = totalTransaksi;
        this.statusTransaksi = statusTransaksi;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getTotalTransaksi() {
        return totalTransaksi;
    }

    public String getStatusTransaksi() {
        return statusTransaksi;
    }
}


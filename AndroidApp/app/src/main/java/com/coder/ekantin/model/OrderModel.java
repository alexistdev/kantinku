package com.coder.ekantin.model;

import com.google.gson.annotations.SerializedName;

public class OrderModel {
    @SerializedName("id")
    private final String idOrder;

    @SerializedName("transaksi_id")
    private final String nomorId;

    @SerializedName("lokasi")
    private final String lokasi;

    @SerializedName("catatan")
    private final String catatan;

    @SerializedName("pelanggan")
    private final String pelanggan;

    @SerializedName("pesanan")
    private final String pesanan;

    @SerializedName("harga")
    private final String harga;

    @SerializedName("status")
    private final String status;

    public OrderModel(String idOrder, String nomorId, String lokasi, String catatan, String pelanggan, String pesanan, String harga, String status) {
        this.idOrder = idOrder;
        this.nomorId = nomorId;
        this.lokasi = lokasi;
        this.catatan = catatan;
        this.pelanggan = pelanggan;
        this.pesanan = pesanan;
        this.harga = harga;
        this.status = status;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public String getNomorId() {
        return nomorId;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getCatatan() {
        return catatan;
    }

    public String getPelanggan() {
        return pelanggan;
    }

    public String getPesanan() {
        return pesanan;
    }

    public String getHarga() {
        return harga;
    }

    public String getStatus() {
        return status;
    }
}

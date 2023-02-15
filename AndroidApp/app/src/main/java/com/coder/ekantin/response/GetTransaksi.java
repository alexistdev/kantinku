package com.coder.ekantin.response;

import com.coder.ekantin.model.TransaksiModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTransaksi {
    @SerializedName("data")
    List<TransaksiModel> daftarTransaksi;

    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private Boolean status;

    public GetTransaksi(List<TransaksiModel> daftarTransaksi, String message, Boolean status) {
        this.daftarTransaksi = daftarTransaksi;
        this.message = message;
        this.status = status;
    }

    public List<TransaksiModel> getDaftarTransaksi() {
        return daftarTransaksi;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}

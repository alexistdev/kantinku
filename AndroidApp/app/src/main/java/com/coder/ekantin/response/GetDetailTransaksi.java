package com.coder.ekantin.response;

import com.coder.ekantin.model.DetailTransaksiModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDetailTransaksi {
    @SerializedName("data")
    List<DetailTransaksiModel> daftarDetailTransaksi;

    @SerializedName("total")
    final private String total;

    @SerializedName("message")
    final private String message;

    @SerializedName("status")
    final private Boolean status;

    public GetDetailTransaksi(List<DetailTransaksiModel> daftarDetailTransaksi, String total, String message, Boolean status) {
        this.daftarDetailTransaksi = daftarDetailTransaksi;
        this.total = total;
        this.message = message;
        this.status = status;
    }

    public List<DetailTransaksiModel> getDaftarDetailTransaksi() {
        return daftarDetailTransaksi;
    }

    public String getTotal() {
        return total;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getStatus() {
        return status;
    }
}

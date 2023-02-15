package com.coder.ekantin.merchant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.coder.ekantin.R;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.model.OrderModel;
import com.coder.ekantin.utils.HelperUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DetailOrderMerchant extends AppCompatActivity {
    private TextView mNamaProduk,mCatatan,mID,mHarga,mLokasi,mTotal;
    private Button mProses,mTolak,mSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_merchant);
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("Detail Order");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        this.dataInit();

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String idTransaksi = extras.getString("idTransaksi","");
            String namaPesanan = extras.getString("namaPesanan","");
            String harga = extras.getString("ciTotal","");
            String lokasi = extras.getString("lokasi","");
            String tipe = extras.getString("tipe","");
            String idOrder = extras.getString("idOrder","");
            mID.setText("#"+idTransaksi);
            mNamaProduk.setText(namaPesanan);
            mHarga.setText(harga);
            mLokasi.setText(lokasi);
            mTotal.setText(harga);
            if(tipe.equals("1")){
                mProses.setVisibility(View.VISIBLE);
                mTolak.setVisibility(View.VISIBLE);
            }
            if(tipe.equals("2")){
                mProses.setVisibility(View.INVISIBLE);
                mTolak.setVisibility(View.INVISIBLE);
                mSelesai.setVisibility(View.VISIBLE);
            }
            mProses.setOnClickListener(v -> {
                if(idOrder != null){
                    doSubmit(getApplicationContext(),"2",idOrder);
                }
            });
            mTolak.setOnClickListener(v -> {
                if(idOrder != null){
                    doSubmit(getApplicationContext(),"4",idOrder);
                }
            });
            mSelesai.setOnClickListener(v -> {
                if(idOrder != null){
                    doSubmit(getApplicationContext(),"3",idOrder);
                    HelperUtils.pesan(getApplicationContext(),"Pesanan Selesai");
                }
            });
        }
    }

    private void doSubmit(Context mContext,String tipe, String orderId){
        try{
            Call<OrderModel> call = APIService.Factory.create(mContext).ubahStatus(orderId,tipe);
            call.enqueue(new Callback<OrderModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                    Intent intent = new Intent(DetailOrderMerchant.this, DashboardMerchant.class);
                    startActivity(intent);
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<OrderModel> call, Throwable t) {
                    if (t instanceof NoConnectivityException) {
                        HelperUtils.pesan(getApplicationContext(), t.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            HelperUtils.pesan(getApplicationContext(), e.getMessage());
        }
    }

    private void dataInit() {
        mNamaProduk = findViewById(R.id.esteh);
        mCatatan = findViewById(R.id.detail);
        mID = findViewById(R.id.txt_id);
        mHarga = findViewById(R.id.txt_harga2);
        mLokasi = findViewById(R.id.txt_lokasi);
        mTotal = findViewById(R.id.txt_total);
        mTolak = findViewById(R.id.btn_tolak);
        mProses = findViewById(R.id.btn_proses);
        mSelesai = findViewById(R.id.btn_selesai);
        mProses.setVisibility(View.INVISIBLE);
        mTolak.setVisibility(View.INVISIBLE);
        mSelesai.setVisibility(View.INVISIBLE);
    }
}
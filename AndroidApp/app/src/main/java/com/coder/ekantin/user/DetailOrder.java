package com.coder.ekantin.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.coder.ekantin.R;
import com.coder.ekantin.adapter.DetailAdapter;
import com.coder.ekantin.adapter.MenuAdapter;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.model.APIError;
import com.coder.ekantin.model.DetailTransaksiModel;
import com.coder.ekantin.response.GetDetailTransaksi;
import com.coder.ekantin.utils.ErrorUtils;
import com.coder.ekantin.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class DetailOrder extends AppCompatActivity {
    private RecyclerView gridItem;
    private ProgressBar progressBar;
    private DetailAdapter detailAdapter;
    private List<DetailTransaksiModel>  daftarDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("Detail Order");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        this.dataInit();
        this.setupRecyclerView();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String getId = extras.getString("idTransaksi","");
            setData(getApplicationContext(),getId);
        }
    }

    private void setData(Context mContext, String idTransaksi){
        this.showDialog();
        try{
            Call<GetDetailTransaksi> call= APIService.Factory.create(mContext).getDetail(idTransaksi);
            call.enqueue(new Callback<GetDetailTransaksi>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetDetailTransaksi> call, Response<GetDetailTransaksi> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            daftarDetail = response.body().getDaftarDetailTransaksi();
                            detailAdapter.replaceData(daftarDetail);
                        }
                    } else {
                        APIError error = ErrorUtils.parseError(response);
                        if (error != null) {
                            HelperUtils.pesan(mContext, error.message());
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<GetDetailTransaksi> call, Throwable t) {
                    hideDialog();
                    if (t instanceof NoConnectivityException) {
                        HelperUtils.pesan(mContext, t.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            hideDialog();
            e.printStackTrace();
            HelperUtils.pesan(getApplicationContext(), e.getMessage());
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        detailAdapter = new DetailAdapter(new ArrayList<>());
        gridItem.setLayoutManager(linearLayoutManager);
        gridItem.setAdapter(detailAdapter);
    }


    private void dataInit() {
        gridItem = findViewById(R.id.rcItem);
        progressBar = findViewById(R.id.progressBar);
        this.hideDialog();
    }

    private void showDialog(){
        if(progressBar.getVisibility() ==  View.INVISIBLE){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideDialog(){
        if(progressBar.getVisibility() ==  View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
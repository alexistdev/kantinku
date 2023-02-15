package com.coder.ekantin.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.coder.ekantin.R;
import com.coder.ekantin.adapter.KeranjangAdapter;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.entity.TotalEntity;
import com.coder.ekantin.model.APIError;
import com.coder.ekantin.model.MenuModel;
import com.coder.ekantin.response.GetKeranjang;
import com.coder.ekantin.utils.ErrorUtils;
import com.coder.ekantin.utils.HelperUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class KeranjangBelanja extends AppCompatActivity implements KeranjangAdapter.ClickListener {
    private RecyclerView gridItem;
    private KeranjangAdapter keranjangAdapter;
    private List<MenuModel> daftarMenu;
    private ProgressBar progressBar;
    private TextView mTotal;
    private Button mCheckout;
    private EditText mLokasi;
    public TotalEntity totalEntity = new TotalEntity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang_belanja);
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("Keranjang Belanja");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                Constants.KEY_USER, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        this.dataInit();
        this.setupRecyclerView();
        setData(getApplicationContext(),idUser);
        mCheckout.setOnClickListener(v -> doSubmit(idUser));
    }

    private void doSave(String lokasi,String idUser){
        String total = totalEntity.getName();
        if(!total.equals("")){
            try{
                this.showDialog();
                Call<MenuModel> call = APIService.Factory.create(getApplicationContext()).checkout(idUser,lokasi,total);
                call.enqueue(new Callback<MenuModel>() {
                    @EverythingIsNonNull
                    @Override
                    public void onResponse(Call<MenuModel> call, Response<MenuModel> response) {
                        Intent intent = new Intent(KeranjangBelanja.this, DashboardUser.class);
                        startActivity(intent);
                        HelperUtils.pesan(getApplicationContext(),"Pesanan anda akan segera datang, mohon ditunggu!");
                    }
                    @EverythingIsNonNull
                    @Override
                    public void onFailure(Call<MenuModel> call, Throwable t) {
                        hideDialog();
                        if (t instanceof NoConnectivityException) {
                            HelperUtils.pesan(getApplicationContext(), t.getMessage());
                        }
                    }
                });
            } catch (Exception e) {
                hideDialog();
                e.printStackTrace();
                HelperUtils.pesan(getApplicationContext(), e.getMessage());
            }
        } else {
            HelperUtils.pesan(getApplicationContext(),"Error Silahkan hubungi Admin!");
        }
    }

    private void doSubmit(String idUser){
        String lokasi = mLokasi.getText().toString();
        if(lokasi.trim().length() > 0){
            this.doSave(lokasi,idUser);
        } else {
            HelperUtils.pesan(getApplicationContext(),"Kolom lokasi wajib diisi!");
        }
    }

    private void dataInit() {
        gridItem = findViewById(R.id.rcItem);
        progressBar = findViewById(R.id.progressBar);
        mTotal = findViewById(R.id.txt_total);
        mCheckout = findViewById(R.id.btn_checkout);
        mLokasi = findViewById(R.id.ed_lokasi);
    }

    private void showDialog() {
        if (progressBar.getVisibility() == View.INVISIBLE) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideDialog() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        daftarMenu = new ArrayList<>();
        keranjangAdapter = new KeranjangAdapter(daftarMenu, this);
        gridItem.setLayoutManager(linearLayoutManager);
        gridItem.setAdapter(keranjangAdapter);
    }

    @Override
    public void dataItemKeranjang(String idItem) {
        this.doDelete(getApplicationContext(),idItem);
    }

    public void doDelete(Context mContext,String idItem)
    {
        this.showDialog();
        try{
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                    Constants.KEY_USER, Context.MODE_PRIVATE);
            String idUser = sharedPreferences.getString("idUser", "");
            Call<MenuModel> call= APIService.Factory.create(mContext).deleteItem(idUser,idItem);
            call.enqueue(new Callback<MenuModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<MenuModel> call, Response<MenuModel> response) {
                    hideDialog();
                    setData(mContext,idUser);
                    HelperUtils.pesan(mContext,"Item berhasil dihapus");
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<MenuModel> call, Throwable t) {
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

    public void setData(Context mContext,String idUser){
        this.showDialog();
        try{

            Call<GetKeranjang> call = APIService.Factory.create(mContext).getCart(idUser);
            call.enqueue(new Callback<GetKeranjang>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetKeranjang> call, Response<GetKeranjang> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            daftarMenu = response.body().getDaftarMenu();
                            keranjangAdapter.replaceData(daftarMenu);
                            mTotal.setText(response.body().getTotal());
                            totalEntity.setName(response.body().getTotal());
                            if(daftarMenu.size() != 0){
                                mCheckout.setVisibility(View.VISIBLE);
                            }

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
                public void onFailure(Call<GetKeranjang> call, Throwable t) {
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
}
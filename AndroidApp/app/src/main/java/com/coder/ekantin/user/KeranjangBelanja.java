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
import android.widget.TextView;
import com.coder.ekantin.R;
import com.coder.ekantin.adapter.KeranjangAdapter;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
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
        this.dataInit();
        this.setupRecyclerView();
        setData(getApplicationContext());
    }

    private void dataInit() {
        gridItem = findViewById(R.id.rcItem);
        progressBar = findViewById(R.id.progressBar);
        mTotal = findViewById(R.id.txt_total);
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
        keranjangAdapter = new KeranjangAdapter(new ArrayList<>(), this);
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
                    setData(mContext);
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

    public void setData(Context mContext){
        this.showDialog();
        try{
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                    Constants.KEY_USER, Context.MODE_PRIVATE);
            String idUser = sharedPreferences.getString("idUser", "");
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
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
import android.widget.ProgressBar;
import com.coder.ekantin.R;
import com.coder.ekantin.adapter.MenuAdapter;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.model.APIError;
import com.coder.ekantin.model.MenuModel;
import com.coder.ekantin.response.GetMenu;
import com.coder.ekantin.ui.Login;
import com.coder.ekantin.utils.ErrorUtils;
import com.coder.ekantin.utils.HelperUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MenuMakanan extends AppCompatActivity implements MenuAdapter.ClickListener {
    private RecyclerView gridMenu;
    private MenuAdapter menuAdapter;
    private List<MenuModel> daftarMenu;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_makanan);
        Toolbar toolbar = findViewById(R.id.tbtoolbarmakanan);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        String setTitleByTipe;
        String getTipe;
        if(extras == null) {
            getTipe = "1";
            setTitleByTipe = "Menu Makanan";
        } else {
            getTipe = extras.getString("tipe");
            setTitleByTipe = "Menu Minuman";
        }
        if (getSupportActionBar() != null) {
            setTitle(setTitleByTipe);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        this.dataInit();
        this.setupRecyclerView();
        this.setData(this, getTipe);
        HelperUtils.pesan(getApplicationContext(),getTipe);
    }

    @Override
    public void dataItemKeranjang(String idMenu) {
       this.addToCart(idMenu,getApplicationContext());
    }



    public void addToCart(String idMenu,Context mContext){
        this.showDialog();
        try{
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                    Constants.KEY_USER, Context.MODE_PRIVATE);
            String idUser = sharedPreferences.getString("idUser", "");

            Call<MenuModel> call = APIService.Factory.create(mContext).addCart(idUser,idMenu);
            call.enqueue(new Callback<MenuModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<MenuModel> call, Response<MenuModel> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Intent intent = new Intent(MenuMakanan.this, DetailOrder.class);
                            startActivity(intent);
                            HelperUtils.pesan(mContext,"Item berhasil ditambahkan!");
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

    public void setData(Context mContext,String tipe){
        this.showDialog();
        try{
            Call<GetMenu> call = APIService.Factory.create(mContext).getMenu(tipe);
            call.enqueue(new Callback<GetMenu>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetMenu> call, Response<GetMenu> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            daftarMenu =response.body().getDaftarMenu();
                            menuAdapter.replaceData(daftarMenu);
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
                public void onFailure(Call<GetMenu> call, Throwable t) {
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

    private void dataInit() {
        gridMenu = findViewById(R.id.rcMenu);
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

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        menuAdapter = new MenuAdapter(new ArrayList<>(), this);
        gridMenu.setLayoutManager(linearLayoutManager);
        gridMenu.setAdapter(menuAdapter);
    }
}
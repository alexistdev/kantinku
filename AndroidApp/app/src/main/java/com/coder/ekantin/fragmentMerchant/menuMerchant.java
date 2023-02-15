package com.coder.ekantin.fragmentMerchant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.coder.ekantin.R;
import com.coder.ekantin.adapter.MenuMerchantAdapter;
import com.coder.ekantin.adapter.TransaksiAdapter;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.merchant.DashboardMerchant;
import com.coder.ekantin.merchant.TambahMenu;
import com.coder.ekantin.model.APIError;
import com.coder.ekantin.model.MenuModelMerchant;
import com.coder.ekantin.response.GetMenuMerchant;
import com.coder.ekantin.ui.Login;
import com.coder.ekantin.utils.ErrorUtils;
import com.coder.ekantin.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class menuMerchant extends Fragment {
    private RecyclerView gridMenu;
    private MenuMerchantAdapter menuMerchantAdapter;
    private List<MenuModelMerchant> daftarMenu= new ArrayList<>();
    private ProgressBar progressBar;
    private ImageView mTambah;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_menu_merchant, container, false);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(
                Constants.KEY_USER, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");

        this.dataInit(mview);
        this.setupRecyclerView();
        this.setData(getContext(),idUser);
        mTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TambahMenu.class);
                startActivity(intent);
            }
        });
        return mview;
    }

    private void dataInit(View mview) {
        gridMenu = mview.findViewById(R.id.rcMenu);
        progressBar = mview.findViewById(R.id.progressBar);
        mTambah = mview.findViewById(R.id.btn_tambah);
        this.hideDialog();
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        daftarMenu = new ArrayList<>();
        menuMerchantAdapter = new MenuMerchantAdapter(daftarMenu);
        gridMenu.setLayoutManager(linearLayoutManager);
        gridMenu.setAdapter(menuMerchantAdapter);
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

    private void setData(Context mContext, String idUser){
        this.showDialog();
        try{
            Call<GetMenuMerchant> call = APIService.Factory.create(mContext).getMenuMerchant(idUser);
            call.enqueue(new Callback<GetMenuMerchant>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetMenuMerchant> call, Response<GetMenuMerchant> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            daftarMenu = response.body().getDaftarMenuMerchant();
                            menuMerchantAdapter.replaceData(daftarMenu);
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
                public void onFailure(Call<GetMenuMerchant> call, Throwable t) {
                    hideDialog();
                    if (t instanceof NoConnectivityException) {
                        HelperUtils.pesan(mContext, t.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            this.hideDialog();
            e.printStackTrace();
            HelperUtils.pesan(getContext(), e.getMessage());
        }
    }
}
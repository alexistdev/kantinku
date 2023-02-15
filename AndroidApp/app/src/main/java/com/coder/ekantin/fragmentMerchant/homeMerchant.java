package com.coder.ekantin.fragmentMerchant;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.coder.ekantin.R;
import com.coder.ekantin.adapter.KeranjangAdapter;
import com.coder.ekantin.adapter.OrderAdapter;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.model.APIError;
import com.coder.ekantin.model.OrderModel;
import com.coder.ekantin.response.GetOrder;
import com.coder.ekantin.utils.ErrorUtils;
import com.coder.ekantin.utils.HelperUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class homeMerchant extends Fragment {
    private RecyclerView gridItem;
    private OrderAdapter orderAdapter;
    private List<OrderModel> daftarOrder;
    private ProgressBar progressBar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_home_merchant, container, false);
        this.dataInit(mview);

        this.setupRecyclerView();
        this.setData(getContext());
        return mview;
    }

    private void dataInit(View mview) {
        gridItem = mview.findViewById(R.id.rcOrder);
        progressBar = mview.findViewById(R.id.progressBar);
        this.hideDialog();
    }

    private void setData(Context mContext){
        this.showDialog();
        try{
            SharedPreferences sharedPreferences = mContext.getSharedPreferences(
                    Constants.KEY_USER, Context.MODE_PRIVATE);
            String idUser = sharedPreferences.getString("idUser", "");
            Call<GetOrder> call= APIService.Factory.create(mContext).getOrder(idUser);
            call.enqueue(new Callback<GetOrder>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetOrder> call, Response<GetOrder> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            daftarOrder = response.body().getDaftarOrder();
                            orderAdapter.replaceData(daftarOrder);
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
                public void onFailure(Call<GetOrder> call, Throwable t) {
                    hideDialog();
                    if (t instanceof NoConnectivityException) {
                        HelperUtils.pesan(mContext, t.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            hideDialog();
            e.printStackTrace();
            HelperUtils.pesan(mContext, e.getMessage());
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        daftarOrder = new ArrayList<>();
        orderAdapter = new OrderAdapter(daftarOrder);
        gridItem.setLayoutManager(linearLayoutManager);
        gridItem.setAdapter(orderAdapter);
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
}
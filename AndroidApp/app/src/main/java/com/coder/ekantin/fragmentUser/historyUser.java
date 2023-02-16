package com.coder.ekantin.fragmentUser;

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
import com.coder.ekantin.adapter.TransaksiAdapter;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.model.APIError;
import com.coder.ekantin.model.TransaksiModel;
import com.coder.ekantin.response.GetTransaksi;
import com.coder.ekantin.utils.ErrorUtils;
import com.coder.ekantin.utils.HelperUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class historyUser extends Fragment {
    private RecyclerView gridHistory;
    private TransaksiAdapter transaksiAdapter;
    private List<TransaksiModel> daftarTransaksi = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_history_user, container, false);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(
                Constants.KEY_USER, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        this.dataInit(mview);
        this.setupRecyclerView();
        this.setData(getContext(),idUser);
        return mview;
    }

    private void dataInit(View mview) {
        gridHistory = mview.findViewById(R.id.rcHistory);
        progressBar = mview.findViewById(R.id.progressBar);
    }
    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        daftarTransaksi = new ArrayList<>();
        transaksiAdapter = new TransaksiAdapter(daftarTransaksi);
        gridHistory.setLayoutManager(linearLayoutManager);
        gridHistory.setAdapter(transaksiAdapter);
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
            Call<GetTransaksi> call= APIService.Factory.create(mContext).getTransaksi(idUser);
            call.enqueue(new Callback<GetTransaksi>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<GetTransaksi> call, Response<GetTransaksi> response) {
                    hideDialog();
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            daftarTransaksi = response.body().getDaftarTransaksi();
                            transaksiAdapter.replaceData(daftarTransaksi);
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
                public void onFailure(Call<GetTransaksi> call, Throwable t) {
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
package com.coder.ekantin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coder.ekantin.R;
import com.coder.ekantin.model.DetailTransaksiModel;
import com.coder.ekantin.model.TransaksiModel;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyHolder>{
    private List<DetailTransaksiModel> mDetailList;

    public DetailAdapter(List<DetailTransaksiModel> mDetailList) {
        this.mDetailList = mDetailList;
    }

    @NonNull
    @Override
    public DetailAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_detail_order, parent, false);
        DetailAdapter.MyHolder holder;
        holder = new DetailAdapter.MyHolder(mView);
        return holder;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private final TextView mNama,mHarga;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            mNama = itemView.findViewById(R.id.txt_nama);
            mHarga = itemView.findViewById(R.id.txt_harga);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DetailAdapter.MyHolder holder, int position) {
        String namaMenu = mDetailList.get(position).getNamaMenu();
        String hargaMenu = mDetailList.get(position).getHarga();
        String ciTotal = holder.itemView.getContext().getString(R.string.rupiah, hargaMenu);
        holder.mHarga.setText(ciTotal);
        holder.mNama.setText(namaMenu);
    }

    @Override
    public int getItemCount() {
        return mDetailList.size();
    }

    public void replaceData(List<DetailTransaksiModel> menuModels) {
        this.mDetailList = menuModels;
        notifyDataSetChanged();
    }
}

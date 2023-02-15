package com.coder.ekantin.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coder.ekantin.R;
import com.coder.ekantin.merchant.DetailOrderMerchant;
import com.coder.ekantin.model.OrderModel;
import com.coder.ekantin.model.TransaksiModel;
import com.coder.ekantin.user.DetailOrder;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder>{
    private List<OrderModel> mOrderList;

    public OrderAdapter(List<OrderModel> mOrderList) {
        this.mOrderList = mOrderList;
    }

    @NonNull
    @Override
    public OrderAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_merchant_order, parent, false);
        OrderAdapter.MyHolder holder;
        holder = new OrderAdapter.MyHolder(mView);
        return holder;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private final TextView mID,mHarga,mStatus;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            mID = itemView.findViewById(R.id.txt_orderID);
            mHarga = itemView.findViewById(R.id.txt_harga);
            mStatus = itemView.findViewById(R.id.txt_status);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String idOrder = mOrderList.get(position).getIdOrder();
        String harga = mOrderList.get(position).getHarga();
        String status = mOrderList.get(position).getStatus();
        String namaPesanan = mOrderList.get(position).getPesanan();
        String lokasi = mOrderList.get(position).getLokasi();
        String cStatus = "Pending";
        if(status.equals("2")){
            cStatus = "Diproses";
        }
        if(status.equals("3")){
            cStatus = "Selesai";
        }
        String cIdTransaksi = holder.itemView.getContext().getString(R.string.order1, idOrder);
        String ciTotal = holder.itemView.getContext().getString(R.string.rupiah, harga);
        holder.mID.setText(cIdTransaksi);
        holder.mHarga.setText(ciTotal);
        holder.mStatus.setText(cStatus);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), DetailOrderMerchant.class);
                mIntent.putExtra("idTransaksi", idOrder);
                mIntent.putExtra("cStatus", status);
                mIntent.putExtra("ciTotal", ciTotal);
                mIntent.putExtra("lokasi", lokasi);
                mIntent.putExtra("namaPesanan", namaPesanan);
                mIntent.putExtra("tipe", status);
                mIntent.putExtra("idOrder", idOrder);
                v.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return mOrderList.size();
    }

    public void replaceData(List<OrderModel> menuModels) {
        this.mOrderList = menuModels;
        notifyDataSetChanged();
    }
}

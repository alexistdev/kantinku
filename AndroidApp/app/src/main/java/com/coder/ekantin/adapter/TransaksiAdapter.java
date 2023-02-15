package com.coder.ekantin.adapter;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.coder.ekantin.R;
import com.coder.ekantin.model.TransaksiModel;
import com.coder.ekantin.user.DetailOrder;
import com.coder.ekantin.utils.HelperUtils;

import java.util.List;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.MyTransaksiHolder>{
    private List<TransaksiModel> mTransakiList;

    public TransaksiAdapter(List<TransaksiModel> mTransakiList) {
        this.mTransakiList = mTransakiList;
    }

    @NonNull
    @Override
    public TransaksiAdapter.MyTransaksiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_order, parent, false);
        TransaksiAdapter.MyTransaksiHolder holder;
        holder = new TransaksiAdapter.MyTransaksiHolder(mView);
        return holder;
    }

    public static class MyTransaksiHolder extends RecyclerView.ViewHolder {
        private final TextView mID,mTotal,mStatus;

        MyTransaksiHolder(@NonNull View itemView) {
            super(itemView);
            mID = itemView.findViewById(R.id.txt_id);
            mTotal = itemView.findViewById(R.id.txt_harga);
            mStatus = itemView.findViewById(R.id.txt_status);
        }
    }

    @Override
    public void onBindViewHolder (@NonNull TransaksiAdapter.MyTransaksiHolder holder, final int position){
        String idTransaksi = mTransakiList.get(position).getIdTransaksi();
        String totalTransaksi = mTransakiList.get(position).getTotalTransaksi();
        String statusTransaksi = mTransakiList.get(position).getStatusTransaksi();
        String cIdTransaksi = holder.itemView.getContext().getString(R.string.order1, idTransaksi);
        String ciTotal = holder.itemView.getContext().getString(R.string.rupiah, totalTransaksi);
        String status = "Pending";
        if(statusTransaksi.equals("2")){
            status = "Complete";
        }

        holder.mID.setText(cIdTransaksi);
        holder.mTotal.setText(ciTotal);
        holder.mStatus.setText(status);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), DetailOrder.class);
                mIntent.putExtra("idTransaksi", idTransaksi);
                v.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount () {
        return mTransakiList.size();
    }

    public void replaceData(List<TransaksiModel> menuModels) {
        this.mTransakiList = menuModels;
        notifyDataSetChanged();
    }


}

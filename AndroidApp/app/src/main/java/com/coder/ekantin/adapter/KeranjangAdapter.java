package com.coder.ekantin.adapter;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coder.ekantin.R;
import com.coder.ekantin.model.MenuModel;

import java.util.List;

public class KeranjangAdapter extends RecyclerView.Adapter<KeranjangAdapter.MyKeranjangHolder>{

    private List<MenuModel> mMenuList;
    public KeranjangAdapter.ClickListener clickListener;

    public KeranjangAdapter(List<MenuModel> mMenuList, KeranjangAdapter.ClickListener clickListener) {
        this.mMenuList = mMenuList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public KeranjangAdapter.MyKeranjangHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        KeranjangAdapter.MyKeranjangHolder holder;
        holder = new KeranjangAdapter.MyKeranjangHolder(mView);
        return holder;
    }

    public static class MyKeranjangHolder extends RecyclerView.ViewHolder {
        private final TextView mNamaMenu,mHarga,mHapus;

        MyKeranjangHolder(@NonNull View itemView) {
            super(itemView);
            mNamaMenu = itemView.findViewById(R.id.txt_nama_menu);
            mHarga = itemView.findViewById(R.id.txt_harga);
            mHapus = itemView.findViewById(R.id.txt_hapus);
        }
    }

    @Override
    public void onBindViewHolder (@NonNull KeranjangAdapter.MyKeranjangHolder holder, final int position){
        String namaMenu = mMenuList.get(position).getNamaMenu();
        String hargaMenu = mMenuList.get(position).getHargaMenu();
        String idMenu = mMenuList.get(position).getIdMenu();
        holder.mNamaMenu.setText(namaMenu);
        holder.mHarga.setText(hargaMenu);
        holder.mHapus.setOnClickListener(v -> clickListener.dataItemKeranjang(idMenu));
    }

    @Override
    public int getItemCount () {
        return mMenuList.size();
    }

    public void replaceData(List<MenuModel> menuModels) {
        this.mMenuList = menuModels;
        notifyDataSetChanged();
    }

    public interface ClickListener{
        void dataItemKeranjang(String msg);
    }

}

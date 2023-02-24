package com.coder.ekantin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.coder.ekantin.R;
import com.coder.ekantin.model.MenuModelMerchant;
import java.util.List;

public class MenuMerchantAdapter extends RecyclerView.Adapter<MenuMerchantAdapter.MyMenuHolder> {

    private List<MenuModelMerchant> mMenuList;
    public MenuMerchantAdapter.ClickListener clickListener;

    public MenuMerchantAdapter(List<MenuModelMerchant> mMenuList, MenuMerchantAdapter.ClickListener clickListener) {
        this.mMenuList = mMenuList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MenuMerchantAdapter.MyMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_merchant_menu, parent, false);
        MenuMerchantAdapter.MyMenuHolder holder;
        holder = new MenuMerchantAdapter.MyMenuHolder(mView);
        return holder;
    }

    public static class MyMenuHolder extends RecyclerView.ViewHolder {
        private final TextView mNamaMenu,mHarga;

        MyMenuHolder(@NonNull View itemView) {
            super(itemView);
            mNamaMenu = itemView.findViewById(R.id.txt_nama_add);
            mHarga = itemView.findViewById(R.id.txt_harga);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyMenuHolder holder, int position) {
        String namaMenu = mMenuList.get(position).getNama();
        String idMenu = mMenuList.get(position).getIdMenu();
        String harga = mMenuList.get(position).getHarga();
        String ciTotal = holder.itemView.getContext().getString(R.string.rupiah, harga);
        holder.mNamaMenu.setText(namaMenu);
        holder.mHarga.setText(ciTotal);
        holder.mNamaMenu.setOnClickListener(v -> clickListener.dataItemKeranjang(idMenu));
    }

    @Override
    public int getItemCount () {
        return mMenuList.size();
    }

    public void replaceData(List<MenuModelMerchant> menuModels) {
        this.mMenuList = menuModels;
        notifyDataSetChanged();
    }

    public interface ClickListener{
        void dataItemKeranjang(String msg);
    }

}

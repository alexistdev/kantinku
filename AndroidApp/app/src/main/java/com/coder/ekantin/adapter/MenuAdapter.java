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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyMenuHolder>{
    private List<MenuModel> mMenuList;
    public MenuAdapter.ClickListener clickListener;

    public MenuAdapter(List<MenuModel> mMenuList, ClickListener clickListener) {
        this.mMenuList = mMenuList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MenuAdapter.MyMenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_menu, parent, false);
        MenuAdapter.MyMenuHolder holder;
        holder = new MenuAdapter.MyMenuHolder(mView);
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
    public void onBindViewHolder (@NonNull MenuAdapter.MyMenuHolder holder, final int position){
        String namaMenu = mMenuList.get(position).getNamaMenu();
        String hargaMenu = mMenuList.get(position).getHargaMenu();
        String idMenu = mMenuList.get(position).getIdMenu();
        holder.mNamaMenu.setText(namaMenu);
        holder.mHarga.setText(hargaMenu);
        holder.mNamaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.dataItemKeranjang(idMenu);
            }
        });
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

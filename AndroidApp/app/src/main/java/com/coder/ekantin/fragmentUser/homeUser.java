package com.coder.ekantin.fragmentUser;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.coder.ekantin.R;
import com.coder.ekantin.user.KeranjangBelanja;
import com.coder.ekantin.user.MenuMakanan;
import com.coder.ekantin.user.MenuMinuman;

public class homeUser extends Fragment {
    private ImageView mKeranjang;
    private CardView mMakanan,mMinuman;


    public homeUser() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_home_user, container, false);
        this.dataInit(mview);
        mKeranjang.setOnClickListener(v -> {
            Intent mIntent = new Intent(v.getContext(), KeranjangBelanja.class);
            startActivity(mIntent);
        });
        mMakanan.setOnClickListener(v -> {
            Intent mIntent = new Intent(v.getContext(), MenuMakanan.class);
            mIntent.putExtra("tipe","1");
            startActivity(mIntent);
        });
        mMinuman.setOnClickListener(v -> {
            Intent mIntent = new Intent(v.getContext(), MenuMakanan.class);
            mIntent.putExtra("tipe","2");
            startActivity(mIntent);
        });
        return mview;
    }

    private void dataInit(View mview) {
        mKeranjang = mview.findViewById(R.id.cart);
        mMakanan = mview.findViewById(R.id.menu_makanan);
        mMinuman = mview.findViewById(R.id.menu_minuman);
    }
}
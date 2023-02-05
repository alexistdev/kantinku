package com.coder.ekantin.fragmentUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coder.ekantin.R;
import com.coder.ekantin.user.KeranjangBelanja;
import com.coder.ekantin.utils.HelperUtils;

public class homeUser extends Fragment {
    private ImageView mKeranjang;


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
        mKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(v.getContext(), KeranjangBelanja.class);
                startActivity(mIntent);
            }
        });
        return mview;
    }

    private void dataInit(View mview) {
        mKeranjang = mview.findViewById(R.id.cart);
//        gridLaundry = mview.findViewById(R.id.rcLaundry);
//        progressBar = mview.findViewById(R.id.progressBar);
    }
}
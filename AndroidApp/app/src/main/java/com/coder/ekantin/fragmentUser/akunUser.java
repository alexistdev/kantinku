package com.coder.ekantin.fragmentUser;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.coder.ekantin.R;
import com.coder.ekantin.ui.Login;
import com.coder.ekantin.utils.HelperUtils;
import com.coder.ekantin.utils.SessionUtils;

import java.util.Objects;

public class akunUser extends Fragment {
    private EditText mNama,mPassword;
    private Button mSimpan,mLogout;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_akun_user, container, false);
        this.dataInit(mview);
        mSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSave();
            }
        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionUtils.logout(requireContext());
                Intent intent = new Intent(getActivity(), Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                if(getActivity()!= null){
                    getActivity().finish();
                }
            }
        });
        return mview;
    }

    private void dataInit(View mview) {
        mNama = mview.findViewById(R.id.NamaLengkap);
        mPassword = mview.findViewById(R.id.txt_password2);
        mSimpan = mview.findViewById(R.id.btn_simpan);
        mLogout = mview.findViewById(R.id.btn_logout);
    }

    private void doSave(){
        String nama = mNama.getText().toString();
        String pass = mPassword.getText().toString();
        if(nama.length() == 0 && pass.length() == 0){
            HelperUtils.pesan(getContext(),"Data tidak mengalami perubahan");
        } else {
            HelperUtils.pesan(getContext(),"Data berhasil disimpan");
        }
    }
}
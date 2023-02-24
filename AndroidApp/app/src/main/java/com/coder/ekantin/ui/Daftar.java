package com.coder.ekantin.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coder.ekantin.R;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.merchant.DashboardMerchant;
import com.coder.ekantin.model.APIError;
import com.coder.ekantin.model.LoginModel;
import com.coder.ekantin.user.DashboardUser;
import com.coder.ekantin.utils.ErrorUtils;
import com.coder.ekantin.utils.HelperUtils;
import com.coder.ekantin.utils.SessionUtils;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class Daftar extends AppCompatActivity {
    private TextView mLogin;
    private ProgressBar progressBar;
    private Button mDaftar;
    private EditText mNama,mEmail,mPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        this.initData();
        this.checkSession();
        mLogin.setOnClickListener(v->{redirecTo(Login.class);});
        mDaftar.setOnClickListener(v->{doRegister();});
    }

    private void doRegister(){
        String nama = mNama.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPass.getText().toString();
        if(nama.trim().length()> 0  && email.trim().length()> 0 && password.trim().length() >0){
            if(!this.cekEmail(email)){
                HelperUtils.pesan(getApplicationContext(),"Masukkan email yang valid !");
            }
            this.doSave(nama,email,password);
        } else {
            HelperUtils.pesan(getApplicationContext(),"Semua kolom harus diisi!");
        }
    }

    private void doSave(String nama, String email, String password){
        this.showDialog();
        try{
            Call<LoginModel> call= APIService.Factory.create(getApplicationContext()).daftarUser(nama,email,password);
            call.enqueue(new Callback<LoginModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    hideDialog();
                    if(response.isSuccessful()) {
                        if(response.body() != null){
                            SessionUtils.login(getApplicationContext(),response.body().getIdUser(),response.body().getRole());
                            if(response.body().getRole().equals("3")){
                                redirecTo(DashboardUser.class);
                            }else{
                                redirecTo(DashboardMerchant.class);
                            }
                        }
                    } else {
                        APIError error = ErrorUtils.parseError(response);
                        if(error != null){
                            HelperUtils.pesan(getApplicationContext(),error.message());
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    hideDialog();
                    if(t instanceof NoConnectivityException) {
                        HelperUtils.pesan(getApplicationContext(),t.getMessage());
                    }
                }
            });
        }catch (Exception e){
            this.hideDialog();
            e.printStackTrace();
            HelperUtils.pesan(getApplicationContext(),e.getMessage());
        }
    }

    private void redirecTo(Class<?> className){
        Intent intent = new Intent(Daftar.this, className);
        startActivity(intent);
        finish();
    }

    private void showDialog(){
        if(progressBar.getVisibility() ==  View.INVISIBLE){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideDialog(){
        if(progressBar.getVisibility() ==  View.VISIBLE){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void initData(){
        mLogin = findViewById(R.id.txt_login);
        mDaftar = findViewById(R.id.btn_daftar);
        mNama = findViewById(R.id.txt_nama);
        mEmail = findViewById(R.id.txt_email);
        mPass = findViewById(R.id.editTextTextPassword);
        progressBar = findViewById(R.id.progressBar);
    }

    private void checkSession() {
        if (SessionUtils.isLoggedIn(this)) {
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                    Constants.KEY_USER, Context.MODE_PRIVATE);
            String role = sharedPreferences.getString("role", "");
            if (Objects.equals(role, "2")) {
                redirecTo(DashboardMerchant.class);
            } else if (Objects.equals(role, "3")) {
                redirecTo(DashboardUser.class);
            } else {
                redirecTo(Login.class);
            }
        }
    }

    private boolean cekEmail(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
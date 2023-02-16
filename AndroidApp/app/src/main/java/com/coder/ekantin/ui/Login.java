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

public class Login extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText mEmail,mPassword;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.initData();
        this.checkSession();
        mLogin.setOnClickListener(v -> doLogin());
    }

    private void checkLogin(String email, String password){

        this.showDialog();
        try{
            Call<LoginModel> call= APIService.Factory.create(getApplicationContext()).loginUser(email,password);
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

    private void doLogin(){
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        if(email.trim().length()> 0 && password.trim().length() >0){
            if(!this.cekEmail(email)){
                HelperUtils.pesan(getApplicationContext(),"Masukkan email yang valid !");
            }
            this.checkLogin(email,password);
        } else {
            HelperUtils.pesan(getApplicationContext(),"Semua kolom harus diisi!");
        }
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

    private void redirecTo(Class<?> className){
        Intent intent = new Intent(Login.this, className);
        startActivity(intent);
        finish();
    }


    private void initData(){

        mEmail = findViewById(R.id.ed_email);
        mPassword = findViewById(R.id.ed_password);
        mLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar);
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

    private boolean cekEmail(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
package com.coder.ekantin.merchant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.coder.ekantin.R;
import com.coder.ekantin.api.APIService;
import com.coder.ekantin.api.Constants;
import com.coder.ekantin.api.NoConnectivityException;
import com.coder.ekantin.entity.TotalEntity;
import com.coder.ekantin.model.APIError;
import com.coder.ekantin.model.MenuModel;
import com.coder.ekantin.utils.ErrorUtils;
import com.coder.ekantin.utils.HelperUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TambahMenu extends AppCompatActivity {
    private TotalEntity totalEntity = new TotalEntity("kosong");
    private Button mSimpan;
    private EditText mNama,mHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_menu);
        Toolbar toolbar = findViewById(R.id.tbtoolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            setTitle("Tambah Menu");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
        this.dataInit();
        mSimpan.setOnClickListener(v -> doSubmit());
    }

    private void doSubmit(){
        String nama = mNama.getText().toString();
        String harga = mHarga.getText().toString();
        if(nama.length() == 0 && harga.length() == 0 && totalEntity.getName().equals("kosong")){
            HelperUtils.pesan(getApplicationContext(),"Silahkan isi form dengan lengkap!");
        } else {
            this.doSave(nama,Integer.parseInt(harga),totalEntity.getName());
        }
    }

    private void doSave(String nama,int harga, String tipe){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(
                Constants.KEY_USER, Context.MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", "");
        try{
            Call<MenuModel> call = APIService.Factory.create(getApplicationContext()).tambahMenu(idUser,nama,harga,tipe);
            call.enqueue(new Callback<MenuModel>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<MenuModel> call, Response<MenuModel> response) {
                    if (response.isSuccessful()) {
                        Intent intent = new Intent(TambahMenu.this, DashboardMerchant.class);
                        startActivity(intent);
                        finish();
                        HelperUtils.pesan(getApplicationContext(),"Data menu berhasil ditambahkan!");
                    } else {
                        APIError error = ErrorUtils.parseError(response);
                        if (error != null) {
                            HelperUtils.pesan(getApplicationContext(), error.message());
                        }
                    }
                }
                @EverythingIsNonNull
                @Override
                public void onFailure(Call<MenuModel> call, Throwable t) {
                    if (t instanceof NoConnectivityException) {
                        HelperUtils.pesan(getApplicationContext(), t.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            HelperUtils.pesan(getApplicationContext(), e.getMessage());
        }
    }

    private void dataInit(){
        mSimpan = findViewById(R.id.btn_simpan);
        mNama = findViewById(R.id.NamaLengkap);
        mHarga = findViewById(R.id.txt_harga);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked)
                    totalEntity.setName("1");
                    break;
            case R.id.radio_ninjas:
                if (checked)
                    totalEntity.setName("2");
                    break;
        }
    }
}
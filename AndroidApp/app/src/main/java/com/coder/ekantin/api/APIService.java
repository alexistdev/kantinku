package com.coder.ekantin.api;

import android.content.Context;

import com.coder.ekantin.BuildConfig;
import com.coder.ekantin.model.LoginModel;
import com.coder.ekantin.model.MenuModel;
import com.coder.ekantin.response.GetMenu;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    String urlAPI = Constants.urlAPI;

    /** tambah item ke dalam keranjang */
    @FormUrlEncoded
    @POST(urlAPI+"cart")
    Call<MenuModel> addCart(@Field("user_id") String userId,
                            @Field("menu_id") String menuId);

    @FormUrlEncoded
    @POST(urlAPI+"auth")
    Call<LoginModel> loginUser(@Field("email") String email,
                               @Field("password") String password);

    @GET(urlAPI+"get_menu")
    Call<GetMenu> getMenu(@Query("tipe") String tipe);

    class Factory {
        public static APIService create(Context mContext) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.addInterceptor(new NetworkConnectionInterceptor(mContext));
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            OkHttpClient client = builder.addInterceptor(logging).build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(APIService.class);
        }
    }
}

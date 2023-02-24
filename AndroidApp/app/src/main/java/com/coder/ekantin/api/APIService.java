package com.coder.ekantin.api;

import android.content.Context;

import com.coder.ekantin.BuildConfig;
import com.coder.ekantin.model.LoginModel;
import com.coder.ekantin.model.MenuModel;
import com.coder.ekantin.model.OrderModel;
import com.coder.ekantin.response.GetDetailTransaksi;
import com.coder.ekantin.response.GetKeranjang;
import com.coder.ekantin.response.GetMenu;
import com.coder.ekantin.response.GetMenuMerchant;
import com.coder.ekantin.response.GetOrder;
import com.coder.ekantin.response.GetTransaksi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIService {
    String urlAPI = Constants.urlAPI;
    String urlAPI2 = Constants.urlAPIMerchant;

    /** tambah item ke dalam keranjang */
    @FormUrlEncoded
    @POST(urlAPI+"cart")
    Call<MenuModel> addCart(@Field("user_id") String userId,
                            @Field("menu_id") String menuId);

    /** CHeckout */
    @FormUrlEncoded
    @POST(urlAPI+"checkout")
    Call<MenuModel> checkout(@Field("user_id") String userId,
                             @Field("lokasi") String lokasi,
                             @Field("total") String total);

    @DELETE(urlAPI+"cart")
    Call<MenuModel> deleteItem(@Query("user_id") String userId,
                            @Query("item_id") String itemId);

    @FormUrlEncoded
    @POST(urlAPI+"auth")
    Call<LoginModel> loginUser(@Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST(urlAPI+"auth/register")
    Call<LoginModel> daftarUser(@Field("nama") String nama,
                                @Field("email") String email,
                                @Field("password") String password);

    @GET(urlAPI+"transaksi/detail")
    Call<GetDetailTransaksi> getDetail(@Query("transaksi_id") String transaksiId);


    @GET(urlAPI+"get_menu")
    Call<GetMenu> getMenu(@Query("tipe") String tipe);

    @GET(urlAPI+"transaksi")
    Call<GetTransaksi> getTransaksi(@Query("user_id") String userId);

    @GET(urlAPI+"cart")
    Call<GetKeranjang> getCart(@Query("user_id") String userId);

    /** MERCHANT */
    @GET(urlAPI2+"order")
    Call<GetOrder> getOrder(@Query("user_id") String userId);

    @DELETE(urlAPI2+"menu")
    Call<MenuModel> deleteMenu(@Query("menu_id") String menuId);

    @FormUrlEncoded
    @POST(urlAPI2+"order/status")
    Call<OrderModel> ubahStatus(@Field("order_id") String orderId,
                               @Field("tipe") String tipe);

    @GET(urlAPI2+"menu")
    Call<GetMenuMerchant> getMenuMerchant(@Query("user_id") String userId);

    @FormUrlEncoded
    @POST(urlAPI2+"menu")
    Call<MenuModel> tambahMenu(@Field("user_id") String userID,
                               @Field("nama") String nama,
                               @Field("harga") int harga,
                               @Field("tipe") String tipe);



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

package com.paisalo.newinternalsourcingapp.Retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //Beta
    //  public static final String BASE_URL = "https://predeptest.paisalo.in:8084/PDL.SourcingApp.Api/api/";
    //Live
    public static final String BASE_URL = "https://pdlmobilelending.paisalo.in:5320/api/";
    public static final String BASE_URL2 = "https://agra.paisalo.in:8444/ESignSBIAV1/";
    public static final String BASE_URL4 = "https://erpservice.paisalo.in:980/PDL.Mobile.API/api/";
    public static final String BASE_URL5 = "https://erpservice.paisalo.in:980/";
    public static final String BASE_URL6 = "https://erpservice.paisalo.in:980/PDL.FiService.API/api/";




    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;
    private static Retrofit retrofit4 = null;
    private static Retrofit retrofit5 = null;
    private static Retrofit retrofit6 = null;

    public static Retrofit  getClient() {
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

            );
            httpClient.connectTimeout(2, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient2() {
        if (retrofit2==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

            );
            httpClient.connectTimeout(2, TimeUnit.MINUTES);
            httpClient.readTimeout(2,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit2;
    }


    public static Retrofit getClient4() {
        if (retrofit4==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

            );
            httpClient.connectTimeout(2, TimeUnit.MINUTES);
            httpClient.readTimeout(2,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit4 = new Retrofit.Builder()
                    .baseUrl(BASE_URL4)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit4;
    }

    public static Retrofit getClient5() {
        if (retrofit5==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

            );
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit5 = new Retrofit.Builder()
                    .baseUrl(BASE_URL5)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit5;
    }
    public static Retrofit getClient6() {
        if (retrofit6==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

            );
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit6 = new Retrofit.Builder()
                    .baseUrl(BASE_URL6)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit6;
    }
}
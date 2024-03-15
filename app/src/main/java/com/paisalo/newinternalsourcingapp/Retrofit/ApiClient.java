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

    public static final String BASE_URL = "https://predeptest.paisalo.in:8084/PDL.SourcingApp.Api/api/";
    public static final String BASE_URL2 = "https://predeptest.paisalo.in:8084/PDL.SourcingApp.Api/api/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder(

            );
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
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
            httpClient.connectTimeout(1, TimeUnit.MINUTES);
            httpClient.readTimeout(1,TimeUnit.MINUTES);
            httpClient.addInterceptor(logging);
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit2;
    }


    public static  Retrofit getClientdynamic(String dbname,String deviceId) {
        Retrofit retrofit = null;
        if (retrofit==null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    Request request = original.newBuilder()
                            .header("userid", deviceId)
                            .header("dbname", dbname)
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
            });

            OkHttpClient client = httpClient.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://erpservice.paisalo.in:980/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}

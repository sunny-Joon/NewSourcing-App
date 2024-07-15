package com.paisalo.newinternalsourcingapp.Activities;

import static android.os.storage.StorageManager.ACTION_CLEAR_APP_CACHE;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.paisalo.newinternalsourcingapp.BuildConfig;
import com.paisalo.newinternalsourcingapp.GlobalClass;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.ProfilePicModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.VersionCheck;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_page);

        Gif();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getAppUpdate();
    }

    private void Gif() {
        ImageView gifImageView = findViewById(R.id.logo);

        // Load the GIF using Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.logo)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, @Nullable Object model, @NonNull com.bumptech.glide.request.target.Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(@NonNull GifDrawable resource, @NonNull Object model, com.bumptech.glide.request.target.Target<GifDrawable> target, @NonNull DataSource dataSource, boolean isFirstResource) {
                        resource.setLoopCount(1); // Set loop count to play only once
                        resource.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
                            @Override
                            public void onAnimationEnd(Drawable drawable) {
                            }
                        });
                        return false;
                    }
                })
                .into(gifImageView);
    }

    private void getAppUpdate(){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<VersionCheck> call=apiInterface.VersionCheck(GlobalClass.dbname, BuildConfig.VERSION_NAME,"S",1);
        Log.d("TAG", "getAppUpdate: "+ BuildConfig.VERSION_NAME);
        call.enqueue(new Callback<VersionCheck>() {
            @Override
            public void onResponse(Call<VersionCheck> call, Response<VersionCheck> response) {
                Log.d("TAG", "getAppUpdate1: "+response.body());

                if(response.isSuccessful()){
                    Log.d("TAG", "getAppUpdate2: ");

                    VersionCheck version = response.body();
                    assert version != null;
                    if(version.getMessage().equals("You are in the Same version !!")){
                        Log.d("TAG", "getAppUpdate3: ");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SplashScreenPage.this, LoginActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                finish();
                            }
                        }, 1200);
                    }else{
                        Intent clearCache = new Intent();
                        clearCache.setAction(ACTION_CLEAR_APP_CACHE);
                        startActivity(clearCache);

                        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenPage.this);
                        builder.setTitle("Need Update");
                        builder.setCancelable(false);
                        builder.setMessage("You are using older version of this app kindly update this app");
                        builder.setPositiveButton("Update Now", (dialog, which) -> {
                            dialog.cancel();
                            String url = version.getData().toString();
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                            finish();
                        });
                        builder.setNegativeButton("Cancel", (dialog, which) -> {
                            // this method is called when user click on negative button.
                            dialog.cancel();
                            finish();
                        });
                        builder.show();
                    }
                }
            }
            @Override
            public void onFailure(Call<VersionCheck> call, Throwable t) {
                Log.d("TAG", "getAppUpdate4: "+t.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenPage.this);
                builder.setTitle("No Internet Connection");
                builder.setCancelable(false);
                builder.setMessage("No Internet Connection, Try again");
                builder.setPositiveButton("Try again", (dialog, which) -> {
                    dialog.cancel();
                    finish();
                    Intent i = new Intent(getApplicationContext(),SplashScreenPage.class);
                    startActivity(i);

                });
                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.cancel();
                    finish();
                });
                builder.show();
            }
        });
    }

}
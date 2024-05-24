package com.paisalo.newinternalsourcingapp.Fragments.home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.slider.Slider;
import com.google.gson.JsonObject;
import com.paisalo.newinternalsourcingapp.Activities.ActivityEarnMoreIncentive;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetCountModel;
import com.paisalo.newinternalsourcingapp.ModelsRetrofit.TargetSetModel;
import com.paisalo.newinternalsourcingapp.R;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiClient;
import com.paisalo.newinternalsourcingapp.Retrofit.ApiInterface;
import com.paisalo.newinternalsourcingapp.Utils.GlobalClass;
import com.paisalo.newinternalsourcingapp.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Dialog popupDialog;
    ImageView imageView;
    static String month = "",year = "";
    String image,stTarget_Popup;
    boolean flag=false;
    AlertDialog dialog;



    public static Fragment newInstance(int position) {
        HomeFragment homeFragment=new HomeFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.backgroundImageView.setVisibility(View.GONE);

        imageView = root.findViewById(R.id.imageTop);
        GifImageView upside = root.findViewById(R.id.upside);
        GifImageView downside = root.findViewById(R.id.downside);

        Calendar calendar = Calendar.getInstance();
        month = new SimpleDateFormat("MMM", Locale.getDefault()).format(calendar.getTime());
        year = new SimpleDateFormat("yyyy", Locale.getDefault()).format(calendar.getTime());

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        image = sharedPreferences.getString("image", "");
        stTarget_Popup = sharedPreferences.getString("stTarget_Popup", "");

        set_image(image);
        if(flag){
            showSliderDialog();
        }

        if(stTarget_Popup.equals("000000")){
            showSliderDialog();
        }else{
            binding.monthlyDisbursmentTarget.setText(stTarget_Popup);
            targetCountApi();
        }

        binding.txtCalcIncentive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.backgroundImageView.setVisibility(View.VISIBLE);

                popupDialog = GlobalClass.showBlurredPopup(requireContext(), root, binding.backgroundImageView, R.layout.incentive_popup);

                new CountDownTimer(2000, 1000) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        startActivity(new Intent(getActivity(), IncentiveAnimatedActivity.class));
                        binding.backgroundImageView.setVisibility(View.GONE);
                        GlobalClass.dismissPopup(popupDialog);                    }
                }.start();
            }
        });

        binding.monthlyDisbursmentTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSliderDialog();

            }
        });

        binding.earnMoreIncentiveLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityEarnMoreIncentive.class);
                intent.putExtra("TvTarget_Screen", binding.monthlyDisbursmentTarget.getText().toString());
                startActivity(intent);
            }
        });

        return root;
    }

    private void targetCountApi() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TargetCountModel> call = apiInterface.getTargetCount( com.paisalo.newinternalsourcingapp.GlobalClass.Token, com.paisalo.newinternalsourcingapp.GlobalClass.dbname,"GRST000223");
        call.enqueue(new Callback<TargetCountModel>() {
            @Override
            public void onResponse(Call<TargetCountModel> call, Response<TargetCountModel> response) {

                if (response.isSuccessful()) {
                    TargetCountModel targetCountModel = response.body();
                    Log.d("TAG_A", "onResponse:123456789 " + targetCountModel);
                    if (targetCountModel.getData() == -1) {
                        binding.targetCount.setText("you are earning Highest incentive");
                    } else {
                        binding.targetCount.setText(targetCountModel.getData().toString() + " people will earn more incentive than you");
                    }
                }
                else
                    Log.d("TAG_A", "onResponse:123456789 " + "else"+response.code());
            }

            @Override
            public void onFailure(Call<TargetCountModel> call, Throwable t) {
                Log.d("TAG_A", "onResponse:123456789 " + "failure");

            }
        });
    }

    private void showSliderDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.target_dialog_content, null);
        dialogBuilder.setView(dialogView);

        // Find the target TextView within the dialog's view hierarchy
        TextView TvTarget_Popup = dialogView.findViewById(R.id.target);
        Slider slider = dialogView.findViewById(R.id.slider);

        if(stTarget_Popup!=null){
            TvTarget_Popup.setText(stTarget_Popup);
        }


        dialog = dialogBuilder.create();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.disbursmentbackground);

        dialog.show();

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                int intValue = (int) value;
                TvTarget_Popup.setText("₹ "+String.valueOf(intValue));
                binding.monthlyDisbursmentTarget.setText("₹ "+String.valueOf(intValue));
            }
        });

        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStartTrackingTouch(Slider slider) {
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onStopTrackingTouch(Slider slider) {
                String input = TvTarget_Popup.getText().toString();
                String Value = input.replaceAll("[^0-9]", "");
                settargetAPI(Value);
                dialog.dismiss();
            }
        });
    }
    private JsonObject JsonOfTarget(String target) {
        Log.d("TAG1", "onResponsemessage: " + target);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("id", 0);
        jsonObject.addProperty("kO_ID", "GRST000223");
        jsonObject.addProperty("targetCommAmt",target);
        jsonObject.addProperty("month",month);
        jsonObject.addProperty("year",year);

        return jsonObject;
    }
    private void settargetAPI(String target) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("stTarget_Popup", target);
        editor.apply();
        Log.d("TAG", "settargetAPIII:11 "+target);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<TargetSetModel> call = apiInterface.setTarget(com.paisalo.newinternalsourcingapp.GlobalClass.Token, com.paisalo.newinternalsourcingapp.GlobalClass.dbname,JsonOfTarget(target));
        call.enqueue(new Callback<TargetSetModel>() {

            @Override
            public void onResponse(Call<TargetSetModel> call, Response<TargetSetModel> response) {
                Log.d("TAG", "settargetAPIII:22 "+response.body());

                if(response.isSuccessful()){
                    Log.d("TAG", "onResponse: "+"success");
                    Log.d("TAG", "onResponsemessage:33 "+response.body());
                    String message = response.body().getMessage();
                    Log.d("TAG", "settargetAPIII:44 "+message);
                    targetCountApi();
                }
                else {
                    Log.d("TAG", "settargetAPIII:response not successful or body is null");
                    Log.d("TAG", "settargetAPIII:response code: " + response.code());
                    Log.d("TAG", "settargetAPIII:response error body: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<TargetSetModel> call, Throwable t) {
                Log.d("TAG4", "settargetAPIII:55 "+"Error");
            }
        });

    }
    private void set_image(String image) {

        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .into(imageView);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.paisalo.newinternalsourcingapp.Fragments.OnBoarding;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OnBoardViewModel extends ViewModel {
     private final MutableLiveData<String>  mutableLiveData;


    public OnBoardViewModel() {
        this.mutableLiveData=new MutableLiveData<>();
        mutableLiveData.setValue("THis is onboarding Frag");

    }

    public LiveData<String> getText(){
        return mutableLiveData;
    }
}

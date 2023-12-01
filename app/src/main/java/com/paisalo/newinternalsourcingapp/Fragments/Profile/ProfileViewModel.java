package com.paisalo.newinternalsourcingapp.Fragments.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mutableLiveData;


    public ProfileViewModel() {
        this.mutableLiveData=new MutableLiveData<>();
        mutableLiveData.setValue("This is Profile Fragment");

    }

    public LiveData<String> getText(){
        return mutableLiveData;
    }
}

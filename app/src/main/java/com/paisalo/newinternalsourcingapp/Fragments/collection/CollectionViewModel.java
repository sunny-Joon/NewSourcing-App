package com.paisalo.newinternalsourcingapp.Fragments.collection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.PortUnreachableException;

public class CollectionViewModel extends ViewModel {
    private final MutableLiveData<String> mutableLiveText;


    public CollectionViewModel() {
        mutableLiveText=new MutableLiveData<>();
        mutableLiveText.setValue("Collection fragment");

    }

    public LiveData<String> getText(){
        return mutableLiveText;
    }
}

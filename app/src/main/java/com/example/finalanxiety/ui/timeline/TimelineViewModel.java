package com.example.finalanxiety.ui.timeline;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.finalanxiety.MainActivity;

import java.util.ArrayList;

public class TimelineViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<String>> dbData;

    public TimelineViewModel() {
        dbData = new MutableLiveData<>();
        dbData.setValue(MainActivity.myBundle.getStringArrayList("cardList"));
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<ArrayList<String>> getDB() {
        return dbData;
    }
}
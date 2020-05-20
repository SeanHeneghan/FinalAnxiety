package com.example.finalanxiety.ui.notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.motivation_messages.MotivationDatabase;

import java.util.ArrayList;

public class NotificationViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> dbData;

    public NotificationViewModel() {
        dbData = new MutableLiveData<>();
        dbData.setValue(MainActivity.myBundle.getStringArrayList("contents"));
    }

    public LiveData<ArrayList<String>> getDB() {
        return dbData;
    }
}
package com.example.finalanxiety.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalanxiety.MainActivity;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> severityDBdata;

    public ProfileViewModel() {
        severityDBdata = new MutableLiveData<>();
        severityDBdata.setValue(MainActivity.myBundle.getStringArrayList("severity_list"));
    }

    public LiveData<ArrayList<String>> getSeverityDB() {
        return severityDBdata;
    }
}

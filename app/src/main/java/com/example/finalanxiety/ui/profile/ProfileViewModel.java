package com.example.finalanxiety.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalanxiety.MainActivity;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> severityDBdata;
    private MutableLiveData<ArrayList<String>> locationDBdata;

    public ProfileViewModel() {
        severityDBdata = new MutableLiveData<>();
        severityDBdata.setValue(MainActivity.myBundle.getStringArrayList("severity_list"));

        locationDBdata = new MutableLiveData<>();
        locationDBdata.setValue(MainActivity.myBundle.getStringArrayList("location_list"));
    }

    public LiveData<ArrayList<String>> getSeverityDB() {
        return severityDBdata;
    }

    public LiveData<ArrayList<String>> getLocationDB() {
        return locationDBdata;
    }
}

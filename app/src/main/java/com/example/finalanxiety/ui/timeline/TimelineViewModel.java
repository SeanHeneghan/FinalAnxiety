package com.example.finalanxiety.ui.timeline;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TimelineViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TimelineViewModel() {
    }

    public LiveData<String> getText() {
        return mText;
    }
}
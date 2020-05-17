package com.example.finalanxiety.ui.document_mood;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DocumentMoodViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DocumentMoodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Document your anxiety below.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
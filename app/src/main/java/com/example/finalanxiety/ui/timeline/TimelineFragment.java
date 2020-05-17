package com.example.finalanxiety.ui.timeline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.R;

public class TimelineFragment extends Fragment {

    private TimelineViewModel timelineViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timelineViewModel =
                ViewModelProviders.of(this).get(TimelineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timeline, container, false);

        String document_timestamp = (String) MainActivity.myBundle.get("date");
        String document_location = (String) MainActivity.myBundle.get("location");
        String document_severity = (String) MainActivity.myBundle.get("severity");
        String document_comments = (String) MainActivity.myBundle.get("comments");

        TextView card_timestamp = root.findViewById(R.id.card_timestamp);

        card_timestamp.setText(document_timestamp);
        card_timestamp.append("\n"+document_location);
        card_timestamp.append("\n"+document_severity);
        card_timestamp.append("\n"+document_comments);

        return root;
    }
}

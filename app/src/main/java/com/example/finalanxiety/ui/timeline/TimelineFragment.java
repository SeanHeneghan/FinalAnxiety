package com.example.finalanxiety.ui.timeline;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.R;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {

    private TimelineViewModel timelineViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timelineViewModel =
                ViewModelProviders.of(this).get(TimelineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timeline, container, false);

        TextView card_timestamp = root.findViewById(R.id.card_timestamp);

        ArrayList<String> card_list = MainActivity.myBundle.getStringArrayList("cardList");
        System.out.println("FRAGMENT CARDS HERE SEAN " + card_list);
        if (card_list != null) {
            String joined = String.join(",", card_list);
            card_timestamp.setText(joined);
        }

        /* String document_timestamp = (String) MainActivity.myBundle.get("date");
        String document_location = (String) MainActivity.myBundle.get("location");
        String document_severity = (String) MainActivity.myBundle.get("severity");
        String document_comments = (String) MainActivity.myBundle.get("comments");

        card_timestamp.setText(document_timestamp);
        card_timestamp.append("\n"+document_location);
        card_timestamp.append("\n"+document_severity);
        card_timestamp.append("\n"+document_comments); */


        return root;
    }
}

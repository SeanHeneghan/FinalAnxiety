package com.example.finalanxiety.ui.timeline;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    int count = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        timelineViewModel =
                ViewModelProviders.of(this).get(TimelineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_timeline, container, false);
        final LinearLayout layout = root.findViewById(R.id.timeline_layout);
        final int pastelGreen = getResources().getColor(R.color.pastelGreen);
        final int white = getResources().getColor(R.color.white);

        timelineViewModel.getDB().observe(getViewLifecycleOwner(), new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> card_list) {
                if (card_list != null) {
                    for (String card : card_list) {
                        count++;
                        System.out.println("NOW");
                        System.out.print(card);
                        System.out.println("-----------------------------");
                        CardView cardView = new CardView(getActivity());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        layoutParams.setMargins(0,16,0,16);
                        cardView.setLayoutParams(layoutParams);
                        cardView.setRadius(30);
                        cardView.setElevation(16);
                        cardView.setCardBackgroundColor(pastelGreen);
                        TextView cardText = new TextView(getActivity());
                        cardText.setTextSize(20);
                        cardText.setTextColor(white);
                        cardText.setPadding(8,8,8,8);
                        cardText.setText(card);
                        cardView.addView(cardText);
                        layout.addView(cardView);
                    }
                }
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences prefs = ((MainActivity)getActivity()).getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);
        String consent = ((MainActivity) getActivity()).getConsent();
        if (!consent.equals("1")) {
            Toast.makeText(getContext(), "Consent not given, shutting down", Toast.LENGTH_SHORT).show();
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    },
                    5000
            );
        }
    }
}

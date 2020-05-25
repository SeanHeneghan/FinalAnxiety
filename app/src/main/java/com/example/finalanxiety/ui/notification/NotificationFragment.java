package com.example.finalanxiety.ui.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.R;
import com.example.finalanxiety.database.CardsDatabase;
import com.example.finalanxiety.database.TimelineCard;
import com.example.finalanxiety.motivation_messages.MotivationDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;
    int count = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotificationViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_notification, container, false);
        final LinearLayout layout = root.findViewById(R.id.notification_layout);
        final int pastelGreen = getResources().getColor(R.color.pastelGreen);
        final int white = getResources().getColor(R.color.white);

        notificationViewModel.getDB().observe(getViewLifecycleOwner(),new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> stack) {
                if (stack != null) {
                    for (final String contents : stack) {
                        CardView cardView = new CardView(getActivity());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        layoutParams.setMargins(0,0,0,16);
                        cardView.setLayoutParams(layoutParams);
                        cardView.setRadius(30);
                        cardView.setCardBackgroundColor(pastelGreen);


                        TextView cardText = new TextView(getActivity());
                        RelativeLayout.LayoutParams text_layout = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                        cardText.setLayoutParams(text_layout);
                        cardText.setTextSize(20);
                        cardText.setTextColor(white);
                        cardText.setPadding(8,8,8,8);
                        cardText.setGravity(Gravity.CENTER);
                        cardText.setText(contents);


                        Button button = new Button(getActivity());
                        RelativeLayout.LayoutParams button_layout = new RelativeLayout.LayoutParams(
                                100,
                                125
                        );
                        button.setLayoutParams(button_layout);
                        button.setText("X");
                        button.getBackground().setAlpha(0);
                        button.setOnClickListener(new View.OnClickListener()
                        {
                            public void onClick(View view)
                            {
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        MotivationDatabase.getInstance(getContext()).motivationAccess().deleteWhere(contents);
                                    }
                                });
                            }
                        });

                        cardView.addView(button);
                        cardView.addView(cardText);

                        layout.addView(cardView);
                        count++;
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

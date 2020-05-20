package com.example.finalanxiety.ui.notification;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalanxiety.R;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private NotificationViewModel notificationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationViewModel =
                ViewModelProviders.of(this).get(NotificationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notification, container, false);
        final LinearLayout layout = root.findViewById(R.id.notification_layout);

        notificationViewModel.getDB().observe(getViewLifecycleOwner(),new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> stack) {
                if (stack != null) {
                    for (String contents : stack) {
                        CardView cardView = new CardView(getActivity());
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        layoutParams.setMargins(0,0,0,16);
                        cardView.setLayoutParams(layoutParams);
                        cardView.setRadius(30);
                        cardView.setCardBackgroundColor(Color.LTGRAY);
                        TextView cardText = new TextView(getActivity());
                        cardText.setTextSize(20);
                        cardText.setGravity(Gravity.CENTER);
                        cardText.setText(contents);
                        cardView.addView(cardText);
                        layout.addView(cardView);
                    }
                }
            }
        });

        return root;
    }
}

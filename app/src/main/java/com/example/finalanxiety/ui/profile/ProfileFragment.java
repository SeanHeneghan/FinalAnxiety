package com.example.finalanxiety.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.R;
import com.example.finalanxiety.motivation_messages.MotivationDatabase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    final ArrayList barEntries = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        TextView severity_x = root.findViewById(R.id.severity_x);
        final BarChart barChart = (BarChart) root.findViewById(R.id.bar_chart);
        barChart.getDescription().setText("");
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getLegend().setEnabled(false);

        profileViewModel.getSeverityDB().observe(getViewLifecycleOwner(),new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> cards) {
                if (cards != null) {
                    Set<String> distinct_values = new HashSet<>(cards);
                    for (String value : distinct_values) {
                        System.out.println(value + ": " + Collections.frequency(cards, value));
                        Float x = Float.parseFloat(value);
                        float y = Collections.frequency(cards,value);
                        BarEntry enter = new BarEntry(x,y);
                        barEntries.add(enter);
                        BarDataSet dataSet = new BarDataSet(barEntries, value);
                        BarData barData = new BarData(dataSet);
                        barChart.setData(barData);
                        barChart.invalidate();
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

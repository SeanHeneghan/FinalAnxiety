package com.example.finalanxiety.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    final ArrayList severityEntries = new ArrayList<>();
    final ArrayList locationEntries = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //severity
        TextView severity_x = root.findViewById(R.id.severity_x);
        final PieChart sev_pieChart = (PieChart) root.findViewById(R.id.sev_pie_chart);
        sev_pieChart.getDescription().setText("");
        sev_pieChart.setEntryLabelColor(Color.BLACK);
        sev_pieChart.getLegend().setEnabled(false);

        profileViewModel.getSeverityDB().observe(getViewLifecycleOwner(),new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> cards) {
                if (cards != null) {
                    Set<String> distinct_values = new HashSet<>(cards);
                    for (String value : distinct_values) {
                        System.out.println(value + ": " + Collections.frequency(cards, value));
                        String x = "Severity " + value;
                        float y = Collections.frequency(cards,value);
                        PieEntry enter = new PieEntry(y, x);
                        severityEntries.add(enter);
                        PieDataSet severityDataSet = new PieDataSet(severityEntries, value);
                        severityDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                        PieData sev_pie_data = new PieData(severityDataSet);
                        sev_pieChart.setData(sev_pie_data);
                        sev_pieChart.invalidate();
                    }
                }
            }
        });

        //location
        TextView location_text = root.findViewById(R.id.location_x);
        final PieChart location_pie_chart = (PieChart) root.findViewById(R.id.location_chart);
        location_pie_chart.getDescription().setText("");
        location_pie_chart.setEntryLabelColor(Color.BLACK);
        location_pie_chart.getLegend().setEnabled(false);

        profileViewModel.getLocationDB().observe(getViewLifecycleOwner(),new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> cards) {
                if (cards != null) {
                    Set<String> distinct_values = new HashSet<>(cards);
                    for (String value : distinct_values) {
                        System.out.println(value + ": " + Collections.frequency(cards, value));
                        float y = Collections.frequency(cards, value);
                        PieEntry enter = new PieEntry(y, value);
                        locationEntries.add(enter);
                        PieDataSet loc_dataSet = new PieDataSet(locationEntries, value);
                        loc_dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                        PieData loc_data = new PieData(loc_dataSet);
                        location_pie_chart.setData(loc_data);
                        location_pie_chart.invalidate();
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

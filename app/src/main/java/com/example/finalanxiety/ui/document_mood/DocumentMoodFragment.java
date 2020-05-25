package com.example.finalanxiety.ui.document_mood;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.R;
import com.example.finalanxiety.database.CardAccess;
import com.example.finalanxiety.database.CardsDatabase;
import com.example.finalanxiety.database.TimelineCard;
import com.example.finalanxiety.ui.timeline.TimelineFragment;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DocumentMoodFragment extends Fragment {

    private DocumentMoodViewModel documentMoodViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        documentMoodViewModel =
                ViewModelProviders.of(this).get(DocumentMoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document_mood, container, false);
        final TextView textView = root.findViewById(R.id.document_intro);
        final CardsDatabase db = CardsDatabase.getInstance(getActivity());

        final TextView date = root.findViewById(R.id.date_document);
        final EditText date_entry = root.findViewById(R.id.document_date_entry);
        final TextView location = root.findViewById(R.id.document_location);
        final EditText location_entry = root.findViewById(R.id.document_location_entry);
        final TextView severity = root.findViewById(R.id.document_severity);
        final EditText severity_entry = root.findViewById(R.id.document_severity_entry);
        final TextView comments = root.findViewById(R.id.document_comments);
        final EditText comments_entry = root.findViewById(R.id.document_comments_entry);

        date.setText(getString(R.string.document_mood_date));
        location.setText(getString(R.string.document_mood_location));
        severity.setText(getString(R.string.document_mood_severity));
        comments.setText(getString(R.string.document_mood_comments));

        final Button submit = root.findViewById(R.id.document_submit);
        submit.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        System.out.println("Grabbing documented information...");
                        final String dat = date_entry.getText().toString();
                        final String loc = location_entry.getText().toString();
                        final String sev = severity_entry.getText().toString();
                        final String com = comments_entry.getText().toString();


                        MainActivity.myBundle.putString("date", dat);
                        MainActivity.myBundle.putString("location", loc);
                        MainActivity.myBundle.putString("severity", sev);
                        MainActivity.myBundle.putString("comments", com);

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                CardsDatabase.getInstance(getContext()).cardAccess().insert(new TimelineCard(dat,loc,sev,com));
                            }
                        });

                        date_entry.getText().clear();
                        location_entry.getText().clear();
                        severity_entry.getText().clear();
                        comments_entry.getText().clear();
                    }
                }
        );

        final Button location_capture = root.findViewById(R.id.location_capture);
        location_capture.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        double latitude = ((MainActivity) getActivity()).sendLatitude();
                        double longitude = ((MainActivity) getActivity()).sendLongitude();
                        System.out.println(latitude);
                        System.out.println(longitude);
                        String address = ((MainActivity) getActivity()).getLocationName(latitude, longitude);
                        System.out.println(address);
                        location_entry.setText(address.substring(0, address.length() -2));
                    }
                }
        );

        final Button time_capture = root.findViewById(R.id.time_now);
        time_capture.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Date time_now = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String formatted_time = dateFormat.format(time_now);
                        System.out.println(formatted_time);
                        date_entry.setText(formatted_time);
                    }
                }
        );

        documentMoodViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
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

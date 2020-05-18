package com.example.finalanxiety.ui.document_mood;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.TrackLocation;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Geocoder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.R;

public class DocumentMoodFragment extends Fragment {

    private DocumentMoodViewModel documentMoodViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        documentMoodViewModel =
                ViewModelProviders.of(this).get(DocumentMoodViewModel.class);
        View root = inflater.inflate(R.layout.fragment_document_mood, container, false);
        final TextView textView = root.findViewById(R.id.document_intro);


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
                        MainActivity.myBundle.putString("date", date_entry.getText().toString());
                        MainActivity.myBundle.putString("location", location_entry.getText().toString());
                        MainActivity.myBundle.putString("severity", severity_entry.getText().toString());
                        MainActivity.myBundle.putString("comments", comments_entry.getText().toString());

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

        documentMoodViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

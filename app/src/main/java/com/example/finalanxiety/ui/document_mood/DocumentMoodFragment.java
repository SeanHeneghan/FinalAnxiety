package com.example.finalanxiety.ui.document_mood;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.R;

import org.w3c.dom.Text;

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

        documentMoodViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

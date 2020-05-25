package com.example.finalanxiety.ui.consent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.finalanxiety.MainActivity;
import com.example.finalanxiety.R;

public class ConsentFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.consent, container, false);
        TextView consent_information = root.findViewById(R.id.consent_text);
        final EditText entered_consent = root.findViewById(R.id.entered_consent);
        Button submit_consent = root.findViewWithTag("submit_consent");
        TextView textView = root.findViewById(R.id.drop_out_text);
        Button remove = root.findViewById(R.id.drop_out);

        submit_consent.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("submitting consent...");
                        final String typed_consent = entered_consent.getText().toString();
                        if (typed_consent.equals("I consent to this")) {
                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE).edit();
                            editor.putString("consent", "1").commit();
                            Toast.makeText(getContext(), "Consent given, thank you.", Toast.LENGTH_SHORT).show();
                            entered_consent.getText().clear();
                        } else {
                            Toast.makeText(getContext(), "Consent given not adequate, please provide exact quotation given", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        remove.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("AppPreferences", Context.MODE_PRIVATE).edit();
                        editor.putString("consent", "0").commit();
                        Toast.makeText(getContext(), "Consent removed, you may now close the application.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        return root;
    }
}

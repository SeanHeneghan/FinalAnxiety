package com.example.finalanxiety.ui.enter_motivation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.finalanxiety.R;
import com.example.finalanxiety.database.CardsDatabase;
import com.example.finalanxiety.database.TimelineCard;
import com.example.finalanxiety.motivation_messages.MotivationCard;
import com.example.finalanxiety.motivation_messages.MotivationDatabase;

public class EnterMotivationFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.enter_notification, container, false);
        final TextView textView = root.findViewById(R.id.notification_intro);
        final EditText editText = root.findViewById(R.id.notification_entered);
        final Button button = root.findViewById(R.id.submit_motivation);

        button.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        System.out.print("Grabbing motivation entry...");
                        final String entry = editText.getText().toString();

                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                MotivationDatabase.getInstance(getContext()).motivationAccess().insert(new MotivationCard(entry));
                            }
                        });

                        editText.getText().clear();
                    }
                }
        );
        return root;
    }
}

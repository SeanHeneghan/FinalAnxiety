package com.example.finalanxiety.motivation_messages;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "motivation")
public class MotivationCard {

    @PrimaryKey(autoGenerate = true)
    private int motivation_id;

    @ColumnInfo(name = "contents")
    private String contents;

    public MotivationCard(int motivation_id, String contents) {
        this.motivation_id = motivation_id;
        this.contents = contents;
    }

    @Ignore
    public MotivationCard(String contents) {
        this.contents = contents;
    }

    public int getMotivation_id() { return motivation_id; }

    public void setMotivation_id(int motivation_id) { this.motivation_id = motivation_id; }

    public String getContents() { return contents; }

    public void setContents(String contents) { this.contents = contents; }
}

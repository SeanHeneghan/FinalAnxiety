package com.example.finalanxiety.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class TimelineCard {

    @PrimaryKey(autoGenerate = true)
    private int card_id;

    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "location")
    private String location;
    @ColumnInfo(name = "severity")
    private String severity;
    @ColumnInfo(name = "comments")
    private String comments;

    public TimelineCard(int card_id, String date, String location, String severity, String comments) {
        this.card_id = card_id;
        this.date = date;
        this.location = location;
        this.severity = severity;
        this.comments = comments;
    }

    @Ignore
    public TimelineCard(String date, String location, String severity, String comments) {
        this.date = date;
        this.location = location;
        this.severity = severity;
        this.comments = comments;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}

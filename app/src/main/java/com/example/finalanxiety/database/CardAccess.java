package com.example.finalanxiety.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardAccess {

    @Query("SELECT * FROM cards")
    List<TimelineCard> getAll();

    @Query("DELETE FROM cards")
    void deleteAll();

    @Insert
    long insert(TimelineCard timelineCard);

    @Update
    void update(TimelineCard timelineCard);

    @Delete
    void delete(TimelineCard timelineCard);
}

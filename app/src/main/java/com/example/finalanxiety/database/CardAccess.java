package com.example.finalanxiety.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardAccess {

    @Query("SELECT * FROM cards")
    LiveData<List<TimelineCard>> getAll();

    @Query("DELETE FROM cards")
    void deleteAll();

    @Insert
    void insert(TimelineCard timelineCard);

    @Update
    void update(TimelineCard timelineCard);

    @Delete
    void delete(TimelineCard timelineCard);
}

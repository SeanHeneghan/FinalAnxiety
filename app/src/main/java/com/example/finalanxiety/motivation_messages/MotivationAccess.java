package com.example.finalanxiety.motivation_messages;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MotivationAccess {

    @Query("SELECT * FROM motivation")
    LiveData<List<MotivationCard>> getAllMotivation();

    @Query("DELETE FROM motivation")
    void deleteAll();

    @Query("DELETE FROM motivation WHERE contents LIKE :input")
    void deleteWhere(String input);

    @Insert
    long insert(MotivationCard motivationCard);

    @Update
    void update(MotivationCard motivationCard);

    @Delete
    void delete(MotivationCard motivationCard);
}

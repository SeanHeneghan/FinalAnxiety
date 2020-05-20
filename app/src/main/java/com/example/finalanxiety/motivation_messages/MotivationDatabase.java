package com.example.finalanxiety.motivation_messages;

import android.content.Context;

import androidx.room.Database;
import androidx.room.PrimaryKey;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = MotivationCard.class, exportSchema = false, version = 1)
public abstract class MotivationDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "motivation";
    private static MotivationDatabase instance;

    public static synchronized MotivationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MotivationDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    public abstract MotivationAccess motivationAccess();
}

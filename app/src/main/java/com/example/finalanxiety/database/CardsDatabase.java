package com.example.finalanxiety.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = TimelineCard.class, exportSchema = false, version = 1)
public abstract class CardsDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "cards";
    private static CardsDatabase instance;

    public static synchronized CardsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CardsDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
    public abstract CardAccess cardAccess();
}

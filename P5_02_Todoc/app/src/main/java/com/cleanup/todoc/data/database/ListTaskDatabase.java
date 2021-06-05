package com.cleanup.todoc.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

@Database(entities = {Project.class, Task.class}, exportSchema = false, version = 1)
public abstract class ListTaskDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "listTask_database.db";
    public static ListTaskDatabase instance;
    private static final Object LOCK = new Object();

    public ListTaskDao listTaskDao;

    public static ListTaskDatabase getInstance(Context context){
        if(instance == null){
            synchronized(LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            ListTaskDatabase.class,DATABASE_NAME)
                            .build();
                }
            }
        }
        return instance;
    }
}

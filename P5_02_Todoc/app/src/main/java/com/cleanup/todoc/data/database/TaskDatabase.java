package com.cleanup.todoc.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import org.jetbrains.annotations.NotNull;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();

    private static TaskDatabase INSTANCE;

    public static TaskDatabase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (TaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "tasks_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    db.execSQL("INSERT INTO pProjects ( pId, nameProject, color ) " +
                                            "VALUES (1, 'Projet Tartampion', '0xFFEADAD1')," +
                                            "(2, 'Projet Lucidia', '0xFFB4CDBA')," +
                                            "(3, 'Projet Circus', '0xFFA3CED2')");
                                }
                            })
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

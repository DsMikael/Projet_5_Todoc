package com.cleanup.todoc.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

    public abstract ProjectDao projectDao();

}

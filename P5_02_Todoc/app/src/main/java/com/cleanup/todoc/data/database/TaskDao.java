package com.cleanup.todoc.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.data.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tTasks ")
    LiveData<List<Task>> getAllTask();

    @Query("SELECT * FROM tTasks WHERE name LIKE :name")
    Task findByName(String name);

    @Query("SELECT * FROM tTasks")
    List<Task> getAllTaskTest();

}

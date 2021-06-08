package com.cleanup.todoc.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.data.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tTtasks ")
    LiveData<List<Task>> getAllTask();

    //Filter A-Z
    @Query("SELECT * FROM tTtasks ORDER BY name ASC")
    LiveData<List<Task>> getFilterAscNameTask();

    //Filter Z-A
    @Query("SELECT * FROM tTtasks ORDER BY name DESC")
    LiveData<List<Task>> getFilterDeskNameTask();

    //Filter Recent
    @Query("SELECT * FROM tTtasks ORDER BY creationTimestamp ASC")
    LiveData<List<Task>>  getFilterAscTimeTask();

    //Filter Oldest
    @Query("SELECT * FROM tTtasks ORDER BY creationTimestamp DESC")
    LiveData<List<Task>>  getFilterDeskTimeTask();



}

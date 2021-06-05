package com.cleanup.todoc.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import java.util.List;

@Dao
public interface ListTaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertProject(Project project);

    @Update
    void updateProject(Project project);

    @Delete
    void deleteProject(Project project);

    @Query("SELECT * FROM project")
    List<Project> getAllProjects();

    @Query("SELECT * FROM project WHERE id=:id")
    Project getProjectById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(Task task);

    @Update
    void updateTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Query("SELECT * FROM task")
    List<Project> getAllTask();

    @Query("SELECT * FROM task WHERE id=:id")
    Project getTaskById(long id);

//    @Insert
//    List<Integer> insertListOfProject(List<Project> project);
//
//    @Update
//    List<Integer> updateListOfProject(List<Project> project);
//
//    @Delete
//    List<Integer> deleteListOfProject(List<Project> project);



}

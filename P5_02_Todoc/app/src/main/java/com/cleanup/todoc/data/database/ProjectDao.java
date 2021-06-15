package com.cleanup.todoc.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.data.model.Project;
import java.util.List;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Query("SELECT * FROM pProjects")
    List<Project> getAllProject();

    @Query("SELECT * FROM pProjects WHERE pId =:pId")
    Project getProject(long pId);

}

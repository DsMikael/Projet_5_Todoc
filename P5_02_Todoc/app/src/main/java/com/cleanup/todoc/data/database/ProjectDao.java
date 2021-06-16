package com.cleanup.todoc.data.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.cleanup.todoc.data.model.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query ("SELECT * FROM pProjects")
    List<Project> getAllProject();
}
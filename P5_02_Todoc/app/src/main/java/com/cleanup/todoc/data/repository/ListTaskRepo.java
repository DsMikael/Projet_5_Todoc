package com.cleanup.todoc.data.repository;

import android.content.Context;

import com.cleanup.todoc.data.database.ListTaskDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListTaskRepo {

    private final ListTaskDatabase listTaskDatabase;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public ListTaskRepo(Context context){
        listTaskDatabase = ListTaskDatabase.getInstance(context);
    }

    public void insertProject(Project project){
        executor.execute(() -> listTaskDatabase.listTaskDao.insertProject(project));
    }
    public void updateProject(Project project){
        executor.execute(() -> listTaskDatabase.listTaskDao.updateProject(project));
    }
    public void deleteProject(Project project){
        executor.execute(() -> listTaskDatabase.listTaskDao.deleteProject(project));
    }

    public List<Project> getAllProjects(){
        return listTaskDatabase.listTaskDao.getAllProjects();
    }

    public void insertTask(Task task){
        executor.execute(() -> listTaskDatabase.listTaskDao.insertTask(task));
    }
    public void updateTask(Task task){
        executor.execute(() -> listTaskDatabase.listTaskDao.updateTask(task));
    }
    public void deleteTask(Task task){
        executor.execute(() -> listTaskDatabase.listTaskDao.deleteTask(task));
    }

}

package com.cleanup.todoc.data.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.database.ProjectDao;
import com.cleanup.todoc.data.database.TaskDao;
import com.cleanup.todoc.data.database.TaskDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kotlin.Lazy;

import static org.koin.java.KoinJavaComponent.inject;

public class ListTaskRepo {

    private final TaskDao taskDao;
    private final ProjectDao projectDao;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public ListTaskRepo(){
        Lazy<TaskDatabase> taskDatabase = inject(TaskDatabase.class);
        projectDao = taskDatabase.getValue().projectDao();
        taskDao = taskDatabase.getValue().taskDao();
    }

    public void insert(Task task){
        executor.execute(() -> taskDao.insert(task));
    }

    public void delete(Task task){
        executor.execute(() -> taskDao.delete(task));
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDao.getAllTask();
    }

    public LiveData<List<Project>> getAllProject() { return projectDao.getAllProject(); }
}
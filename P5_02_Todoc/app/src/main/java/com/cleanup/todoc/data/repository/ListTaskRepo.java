package com.cleanup.todoc.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.data.database.ProjectDao;
import com.cleanup.todoc.data.database.TaskDao;
import com.cleanup.todoc.data.database.TaskDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.koin.java.KoinJavaComponent.inject;
import kotlin.Lazy;

public class ListTaskRepo {

    private final Lazy<TaskDatabase> taskDatabase = inject(TaskDatabase.class);
    private final TaskDao taskDao;
    private final ProjectDao projectDao;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public ListTaskRepo(Application application){
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
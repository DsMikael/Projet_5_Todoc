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

import timber.log.Timber;

public class ListTaskRepo {
    private final TaskDao taskDao;
    private final ProjectDao projectDao;
    private final LiveData<List<Task>> allTasks;
    private final Project[] allProjects;

    private final Executor executor = Executors.newSingleThreadExecutor();

    public ListTaskRepo(Application application){
        TaskDatabase taskDatabase = TaskDatabase.getInstance(application);

        taskDao = taskDatabase.taskDao();
        projectDao = taskDatabase.projectDao();
        allTasks = taskDao.getAllTask();
        allProjects = Project.getAllProjects();
//        if (allProjects.size() == 0){
//            insertProject();
//        }
        Timber.d(String.valueOf(projectDao.getAllProject()));

    }

    public void insert(Task task){
        executor.execute(() -> taskDao.insert(task));
    }

    public void delete(Task task){
        executor.execute(() -> taskDao.delete(task));
    }

    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public void insertProject(){
            executor.execute(() -> projectDao.insert(
                    new Project(1L, "Projet Tartampion", 0xFFEADAD1)));
            executor.execute(() -> projectDao.insert(
                    new Project(2L, "Projet Lucidia", 0xFFB4CDBA)));
            executor.execute(() -> projectDao.insert(
                    new Project(3L, "Projet Circus", 0xFFA3CED2)));
    }

 //   public List<Project> getAllProject() { return allProjects; }

}

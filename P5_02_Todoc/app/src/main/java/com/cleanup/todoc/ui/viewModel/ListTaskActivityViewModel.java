package com.cleanup.todoc.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.data.repository.ListTaskRepo;
import com.cleanup.todoc.ui.adapter.TasksAdapter;
import com.cleanup.todoc.ui.view.ListTaskActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class ListTaskActivityViewModel extends AndroidViewModel {
    /**
     * List of all projects available in the application
     */
    public final Project[] allProjects = Project.getAllProjects();

    /**
     * List of all current tasks of the application
     */
    @NonNull
    public final ArrayList<Task> tasks = new ArrayList<>();

    public MutableLiveData<ArrayList<Task>> tasksLiveData = new MutableLiveData<>();

    private ListTaskRepo listTaskRepo;

    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    public SortMethod sortMethod = SortMethod.NONE;

    public ListTaskActivityViewModel(@NonNull @NotNull Application application) {
        super(application);

        listTaskRepo = new ListTaskRepo(application);
    }
    public void refreshList() {
        Timber.d("RefrechList");
        tasksLiveData.postValue(tasks);
    }

    public void onDeleteTask(Task task) {
        tasks.remove(task);
    }
    /**
     * Adds the given task to the list of created tasks.
     *
     * @param task the task to be added to the list
     */
    public void addTask(@NonNull Task task) {

        tasks.add(task);
        refreshList();
    }

    public boolean checkTask() {
        if (tasks.size() == 0) {
            return true;
        } else {
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasks, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new Task.TaskOldComparator());
                    break;
            }
            refreshList();
            return false;
        }
    }

    public void insertProject(Project project){
        listTaskRepo.insertProject(project);
    }

    public List<Project> getAllProjects(){
        return listTaskRepo.getAllProjects();
    }

    public void insertTask(Task task){
        listTaskRepo.insertTask(task);

    }
    public void updateTask(Task task){
        listTaskRepo.insertTask(task);
    }
    public void deleteTask(Task task){
        listTaskRepo.insertTask(task);
    }

    /**
     * List of all possible sort methods for task
     */
    public enum SortMethod {
        /**
         * Sort alphabetical by name
         */
        ALPHABETICAL,
        /**
         * Inverted sort alphabetical by name
         */
        ALPHABETICAL_INVERTED,
        /**
         * Lastly created first
         */
        RECENT_FIRST,
        /**
         * First created first
         */
        OLD_FIRST,
        /**
         * No sort
         */
        NONE
    }

}

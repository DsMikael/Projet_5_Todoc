package com.cleanup.todoc.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.data.repository.ListTaskRepo;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import timber.log.Timber;


public class ListTaskActivityViewModel extends AndroidViewModel {
    /**
     * List of all current tasks of the application
     */
    private final ListTaskRepo mListTaskRepo;
    public final MediatorLiveData<List<Task>> mAllTask = new MediatorLiveData<>();
    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    public MutableLiveData<SortMethod> sortMethod = new MutableLiveData<>(SortMethod.NONE);

    public ListTaskActivityViewModel(@NonNull @NotNull Application application) {
        super(application);
        mListTaskRepo = new ListTaskRepo(application);
        mAllTask.addSource(mListTaskRepo.getAllTasks(),tasks -> {
            mAllTask.setValue(tasks);
            mAllTask.postValue(checkTask());
        });
        mAllTask.addSource(sortMethod, sortMethod1 -> mAllTask.postValue(checkTask()));

    }


    public void onDeleteTask(Task task) {
        mListTaskRepo.delete(task);
    }
    /**
     * Adds the given task to the list of created tasks.
     *
     * @param task the task to be added to the list
     */
    public void addTask(@NonNull Task task) {
        mListTaskRepo.insert(task);
    }

    public List<Task> checkTask() {
        final List<Task> tasks = mAllTask.getValue();
        switch (sortMethod.getValue()) {
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
            return tasks;
    }

    public static Project[] getAllProjects() {
        return Project.getAllProjects();
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

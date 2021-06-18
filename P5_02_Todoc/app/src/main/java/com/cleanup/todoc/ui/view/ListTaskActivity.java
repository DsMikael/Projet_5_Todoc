package com.cleanup.todoc.ui.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cleanup.todoc.R;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.databinding.ActivityMainBinding;
import com.cleanup.todoc.ui.adapter.TasksAdapter;
import com.cleanup.todoc.ui.viewModel.ListTaskActivityViewModel;

import java.util.Date;

import timber.log.Timber;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class ListTaskActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener{

    private ListTaskActivityViewModel viewModel;
    private  ActivityMainBinding binding;

    /**
     * Dialog to create a new task
     */
    @Nullable
    public AlertDialog dialog = null;

    /**
     * EditText that allows user to set the name of a task
     */
    @Nullable
    private EditText dialogEditText = null;

    /**
     * Spinner that allows the user to associate a project to a task
     */
    @Nullable
    private Spinner dialogSpinner = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                                     .get(ListTaskActivityViewModel.class);
        binding.listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        binding.fabAddTask.setOnClickListener(v -> showAddTaskDialog());

            viewModel.mAllTask.observe(this, tasks -> {
                if (tasks != null) {
                    Timber.d("Observe Task%s", tasks.size());
                }
            });

        observeTasks();
    }

    private void observeTasks() {
        viewModel.mAllTask.observe(this,
                tasks -> {
                    if(tasks != null) {
                        binding.listTasks.setAdapter(new TasksAdapter(tasks, this));
                        updateTasks(tasks.size());
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) {
            viewModel.sortMethod.setValue(ListTaskActivityViewModel.SortMethod.ALPHABETICAL);
        } else if (id == R.id.filter_alphabetical_inverted) {
            viewModel.sortMethod.setValue(ListTaskActivityViewModel.SortMethod.ALPHABETICAL_INVERTED);
        } else if (id == R.id.filter_oldest_first) {
            viewModel.sortMethod.setValue(ListTaskActivityViewModel.SortMethod.OLD_FIRST);
        } else if (id == R.id.filter_recent_first) {
            viewModel.sortMethod.setValue(ListTaskActivityViewModel.SortMethod.RECENT_FIRST);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Called when the user clicks on the positive button of the Create Task Dialog.
     *
     * @param dialogInterface the current displayed dialog
     */
    private void onPositiveButtonClick(DialogInterface dialogInterface) {
        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if (dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }
            // If both project and name of the task have been set
            else if (taskProject != null) {

                Task task = new Task(
                        0,
                        taskProject,
                        taskName,
                        new Date().getTime()
                );

                viewModel.addTask(task);
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else{
                dialogInterface.dismiss();
            }
        }
        // If dialog is aloready closed
        else {
            dialogInterface.dismiss();
        }
    }

    /**
     * Shows the Dialog for adding a Task
     */
    private void showAddTaskDialog() {
        final AlertDialog dialog = getAddTaskDialog();

        dialog.show();

        dialogEditText = dialog.findViewById(R.id.txt_task_name);
        dialogSpinner = dialog.findViewById(R.id.project_spinner);

        populateDialogSpinner();
    }

    /**
     * Updates the list of tasks in the UI
     * @param size
     */
    private void updateTasks(int size) {
        if(size == 0){
            binding.lblNoTask.setVisibility(View.VISIBLE);
            binding.listTasks.setVisibility(View.GONE);
        }else{
            binding.lblNoTask.setVisibility(View.GONE);
            binding.listTasks.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Returns the dialog allowing the user to create a new task.
     *
     * @return the dialog allowing the user to create a new task
     */
    @NonNull
    private AlertDialog getAddTaskDialog() {
        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this, R.style.Dialog);

        alertBuilder.setTitle(R.string.add_task);
        alertBuilder.setView(R.layout.dialog_add_task);
        alertBuilder.setPositiveButton(R.string.add, null);
        alertBuilder.setOnDismissListener(dialogInterface -> {
            dialogEditText = null;
            dialogSpinner = null;
            dialog = null;
        });

        dialog = alertBuilder.create();

        // This instead of listener to positive button in order to avoid automatic dismiss
        dialog.setOnShowListener(dialogInterface -> {

            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view -> onPositiveButtonClick(dialog));
        });

        return dialog;
    }
    public void onDeleteTask(Task task) {
        viewModel.onDeleteTask(task);
    }
    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    private void populateDialogSpinner() {
        final ArrayAdapter<Project> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, viewModel.getAllProjects());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

}

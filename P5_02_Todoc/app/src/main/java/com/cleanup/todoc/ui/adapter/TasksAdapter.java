package com.cleanup.todoc.ui.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;
import com.cleanup.todoc.databinding.ItemTaskBinding;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {
    /**
     * The list of tasks the adapter deals with
     */
    @NonNull
    private final List<Task> mtasks;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     * @param tasks the list of tasks the adapter deals with to set
     * @param deleteTaskListener
     */
    public TasksAdapter(@NonNull List<Task> tasks,
                        @NonNull  DeleteTaskListener deleteTaskListener) {
        mtasks = tasks;
        this.deleteTaskListener = deleteTaskListener;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        final ItemTaskBinding binding = ItemTaskBinding.inflate(
                LayoutInflater.from(viewGroup.getContext()), viewGroup,false);
        return new TaskViewHolder(binding, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {
       taskViewHolder.bind(mtasks.get(position));
 //       taskViewHolder.binding.setTask(mtasks.get(position));
    }

    @Override
    public int getItemCount() {
        return mtasks.size();
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param task the task that needs to be deleted
         */
        void onDeleteTask(Task task);
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        private final ItemTaskBinding binding;
        /**
         * The listener for when a task needs to be deleted
         */
        private final DeleteTaskListener deleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param binding the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        public TaskViewHolder(@NonNull ItemTaskBinding binding, @NonNull DeleteTaskListener deleteTaskListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.deleteTaskListener = deleteTaskListener;
            binding.imgDelete.setOnClickListener(view -> {
                final Object tag = view.getTag();
                if (tag instanceof Task) {
                    TaskViewHolder.this.deleteTaskListener.onDeleteTask((Task) tag);
                }
            });
        }
        /**
         * Binds a task to the item view.
         *
         * @param task the task to bind in the item view
         */
        public void bind(Task task) {
            binding.lblTaskName.setText(task.getName());
            binding.imgDelete.setTag(task);
            final Project taskProject = task.getProject();
            if (taskProject != null) {
                binding.imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                binding.lblProjectName.setText(taskProject.getName());
                binding.setTask(task);
            } else {
                binding.imgProject.setVisibility(View.INVISIBLE);
                binding.lblProjectName.setText("");
            }

        }
    }
}

package com.cleanup.todoc.data.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "tTasks")
public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tId")
    private final long id;

    /**
     * The unique identifier of the project associated to the task
     */
    @SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
    @Embedded
    private final Project project;

    /**
     * The name of the task
     */
    @ColumnInfo(name = "name")
    private final String name;

    /**
     * The timestamp when the task has been created
     */
    @ColumnInfo(name = "creationTimestamp")
    private final long creationTimestamp;

    @Override
    public @NotNull String toString() {
        return "Task{" +
                "id=" + id +
                ", project=" + project +
                ", name='" + name + '\'' +
                ", creationTimestamp=" + creationTimestamp +
                '}';
    }

    /**
     * Instantiates a new Task.
     *
     * @param id                the unique identifier of the task to set
     * @param project         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public Task(long id, Project project, @NonNull String name, long creationTimestamp) {
        this.id = id;
        this.project = project;
        this.name = name;
        this.creationTimestamp = creationTimestamp;
    }

    /**
     * Returns the unique identifier of the task.
     *
     * @return the unique identifier of the task
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the project associated to the task.
     *
     * @return the project associated to the task
     */
    @Nullable
    public Project getProject() {
        return project;
    }

    /**
     * Returns the name of the task.
     *
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    public long getCreationTimestamp() { return creationTimestamp; }

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.name.compareTo(right.name);
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.name.compareTo(left.name);
        }
    }

    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.creationTimestamp - left.creationTimestamp);
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.creationTimestamp - right.creationTimestamp);
        }
    }
}

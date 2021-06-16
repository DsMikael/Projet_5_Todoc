package com.cleanup.todoc;

import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {
    final Project project1 = new Project(1,"Projet Tartampion",0xFFEADAD1);
    final Project project2 = new Project(2,"Projet Lucidia",0xFFB4CDBA);
    final Project project3 = new Project(3,"Projet Circus",0xFFA3CED2);

    final Task taskComparator1 = new Task(1, project1, "aaa", 123);
    final Task taskComparator2 = new Task(2, project2, "zzz", 124);
    final Task taskComparator3 = new Task(3, project3, "hhh", 125);
    final Task taskComparator4 = new Task(3, null, "rrr", 126);

    @Test
    public void test_projects() {
        assert taskComparator1.getProject() != null;
        assert taskComparator2.getProject() != null;
        assert taskComparator3.getProject() != null;
        assertEquals("Projet Tartampion", taskComparator1.getProject().getName());
        assertEquals("Projet Lucidia", taskComparator2.getProject().getName());
        assertEquals("Projet Circus", taskComparator3.getProject().getName());
        assertNull(taskComparator4.getProject());
    }

    @Test
    public void test_az_comparator() {
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(taskComparator1);
        tasks.add(taskComparator2);
        tasks.add(taskComparator3);
        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), taskComparator1);
        assertSame(tasks.get(1), taskComparator3);
        assertSame(tasks.get(2), taskComparator2);
    }

    @Test
    public void test_za_comparator() {
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(taskComparator1);
        tasks.add(taskComparator2);
        tasks.add(taskComparator3);
        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), taskComparator2);
        assertSame(tasks.get(1), taskComparator3);
        assertSame(tasks.get(2), taskComparator1);
    }

    @Test
    public void test_recent_comparator() {
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(taskComparator1);
        tasks.add(taskComparator2);
        tasks.add(taskComparator3);
        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), taskComparator3);
        assertSame(tasks.get(1), taskComparator2);
        assertSame(tasks.get(2), taskComparator1);
    }

    @Test
    public void test_old_comparator() {
        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(taskComparator1);
        tasks.add(taskComparator2);
        tasks.add(taskComparator3);
        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), taskComparator1);
        assertSame(tasks.get(1), taskComparator2);
        assertSame(tasks.get(2), taskComparator3);
    }
}
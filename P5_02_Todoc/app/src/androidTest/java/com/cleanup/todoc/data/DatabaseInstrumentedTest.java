package com.cleanup.todoc.data;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.data.database.ProjectDao;
import com.cleanup.todoc.data.database.TaskDao;
import com.cleanup.todoc.data.database.TaskDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import kotlin.Lazy;

import static com.cleanup.todoc.utils.LiveDataTestUtil.getOrAwaitValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.koin.java.KoinJavaComponent.inject;
@RunWith (AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private TaskDao taskDao;
    private ProjectDao projectDao;
    private final Lazy<TaskDatabase> database = inject(TaskDatabase.class);

    @Rule
    public InstantTaskExecutorRule taskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb(){
        taskDao = database.getValue().taskDao();
        projectDao = database.getValue().projectDao();
    }

    @After
    public void closeDb() {
        database.getValue().close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception{
        final long time = new Date().getTime();

        Project project = new Project(1, "Projet Tartampion", 0xFFEADAD1);
        Task task = new Task(0, project,"test2", time);

        assertEquals(3, getOrAwaitValue(projectDao.getAllProject()).size());

        taskDao.insert(task);
        final List<Task> taskList = getOrAwaitValue(taskDao.getAllTask());

        assertEquals(1, taskList.size());
        final Task task1 = taskList.get(0);

        assertEquals("test2", task1.getName());
        assertEquals(time, task1.getCreationTimestamp());

        assertNotNull(task1.getProject());
        assertEquals(1, task1.getProject().getId());
        assertEquals("Projet Tartampion", task1.getProject().getName());
        assertEquals(0xFFEADAD1, task1.getProject().getColor());

        taskDao.delete(task);
        assertFalse(getOrAwaitValue(taskDao.getAllTask()).contains(task));
    }
}

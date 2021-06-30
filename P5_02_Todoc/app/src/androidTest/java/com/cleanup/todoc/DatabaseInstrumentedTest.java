package com.cleanup.todoc;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cleanup.todoc.data.database.ProjectDao;
import com.cleanup.todoc.data.database.TaskDao;
import com.cleanup.todoc.data.database.TaskDatabase;
import com.cleanup.todoc.data.model.Project;
import com.cleanup.todoc.data.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import kotlin.Lazy;

import static com.cleanup.todoc.utils.LiveDataTestUtil.getOrAwaitValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.koin.java.KoinJavaComponent.inject;


@RunWith (AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    private TaskDao taskDao;
    private ProjectDao projectDao;
    private final Lazy<TaskDatabase> database = inject(TaskDatabase.class);

    @Before
    public void createDb(){
        taskDao = database.getValue().taskDao();
        projectDao = database.getValue().projectDao();
    }

    @After
    public void closeDb() throws IOException {
        database.getValue().close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception{
        Project project = new Project(1, "Projet Tartampion", 0xFFEADAD1);
        Task task = new Task(0, project,"test2", new Date().getTime());

        taskDao.insert(task);

        assertEquals(1, getOrAwaitValue(projectDao.getAllProject()).size());

        Task tNameTask = taskDao.findByName(task.getName());
        assertEquals(tNameTask.getName(), task.getName());

        taskDao.delete(task);
        assertFalse(getOrAwaitValue(taskDao.getAllTask()).contains(task.getName()));




    }
}

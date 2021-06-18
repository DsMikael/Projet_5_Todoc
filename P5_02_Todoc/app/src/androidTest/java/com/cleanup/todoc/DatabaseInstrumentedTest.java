package com.cleanup.todoc;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


@RunWith (AndroidJUnit4.class)
public class DatabaseInstrumentedTest {
    private TaskDao taskDao;
    private ProjectDao projectDao;
    private TaskDatabase database;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TaskDatabase.class).build();
        taskDao = database.taskDao();
        projectDao = database.projectDao();
    }

    @After
    public void closeDb() throws IOException {
        database.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception{
        Project project = new Project(1, "Projet Tartampion", 0xFFEADAD1);
        Task task = new Task(0, project,"test2", new Date().getTime());

        taskDao.insert(task);
        projectDao.insertTest(project);

        assertEquals(1, projectDao.getAllProject().size());

        Task tNameTask = taskDao.findByName(task.getName());
        assertEquals(tNameTask.getName(), task.getName());

        taskDao.delete(task);
        assertFalse(taskDao.getAllTaskTest().contains(task.getName()));




    }
}

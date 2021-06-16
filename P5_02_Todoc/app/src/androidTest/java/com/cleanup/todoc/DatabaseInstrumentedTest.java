package com.cleanup.todoc;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;


@RunWith (AndroidJUnit4.class)
public class DatabaseInstrumentedTest {
    private TaskDao taskDao;
    private TaskDatabase database;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TaskDatabase.class).build();
        taskDao = database.taskDao();
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
        Task tNameTask = taskDao.findByName(task.getName());
        assertThat(tNameTask.getName(), equalTo(task.getName()));

        taskDao.delete(task);
        assertFalse(taskDao.getAllTaskTest().contains(task.getName()));

    }
}

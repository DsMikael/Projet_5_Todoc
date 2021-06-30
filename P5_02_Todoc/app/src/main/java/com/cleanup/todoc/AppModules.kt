package com.cleanup.todoc;

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cleanup.todoc.data.database.TaskDatabase
import org.koin.dsl.module

var ProjectDbSql = "INSERT INTO pProjects ( pId, nameProject, color ) " +
        "VALUES (1, 'Projet Tartampion', '0xFFEADAD1')," +
        "(2, 'Projet Lucidia', '0xFFB4CDBA')," +
        "(3, 'Projet Circus', '0xFFA3CED2')"

/**
 *  Room Database definition
 */
val appDataModule = module {
    fun appDatabase(context: Context): TaskDatabase
    {
        return  Room.databaseBuilder(context, TaskDatabase::class.java, "tasks_db")
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL(ProjectDbSql)
                    }
                })
                .build()
    }

    single {
        appDatabase(get())
    }
}
/**
 * In-Memory Room Database definition
 */
val appDataTestModule = module {
    fun appTestDatabase(context: Context): TaskDatabase
    {
        return Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL(ProjectDbSql)
                    }
                })
                .build()
    }

    single {
        appTestDatabase(get())
    }
}
package com.cleanup.todoc;

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
    single {
        var database = Room.databaseBuilder(get(), TaskDatabase::class.java, "tasks_db")
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL(ProjectDbSql)
                    }
                })
                .build()

    }


}
/**
 * In-Memory Room Database definition
 */
val appDataTestModule = module {
    factory {
        var database = Room.inMemoryDatabaseBuilder(get(), TaskDatabase::class.java)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        db.execSQL(ProjectDbSql)
                    }
                })
                .build()
    }
}
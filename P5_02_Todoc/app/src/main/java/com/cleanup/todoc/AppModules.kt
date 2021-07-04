package com.cleanup.todoc

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cleanup.todoc.data.database.TaskDatabase
import org.koin.dsl.module

const val ProjectDbSql = "INSERT INTO pProjects ( pId, nameProject, color ) " +
        "VALUES (1, 'Projet Tartampion', '0xFFEADAD1')," +
        "(2, 'Projet Lucidia', '0xFFB4CDBA')," +
        "(3, 'Projet Circus', '0xFFA3CED2')"

/**
 *  Room Database definition
 */
val appDataModule = module {
    single {
        Room.databaseBuilder(get(), TaskDatabase::class.java, "tasks_db").apply {
            fallbackToDestructiveMigration()
            addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL(ProjectDbSql)
                }
            })
        }.build()
    }
}

/**
 * In-Memory Room Database definition
 */
val appDataTestModule = module {
    single {
        Room.inMemoryDatabaseBuilder(get(), TaskDatabase::class.java).apply {
            addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                db.execSQL(ProjectDbSql)
            }
        })
        }.build()
    }
}
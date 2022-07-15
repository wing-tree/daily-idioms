package com.wing.tree.bruni.daily.idioms.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wing.tree.bruni.daily.idioms.data.dao.IdiomDao
import com.wing.tree.bruni.daily.idioms.data.dao.QuestionDao
import com.wing.tree.bruni.daily.idioms.data.entity.Idiom
import com.wing.tree.bruni.daily.idioms.data.entity.Question
import com.wing.tree.bruni.daily.idioms.data.typeconverter.TypeConverters

@androidx.room.Database(
    entities = [Idiom::class, Question::class],
    exportSchema = false,
    version = 3
)
@androidx.room.TypeConverters(TypeConverters::class)
abstract class Database: RoomDatabase() {
    abstract fun idiomDao(): IdiomDao
    abstract fun questionDao(): QuestionDao

    companion object {
        private const val PACKAGE_NAME = "com.wing.tree.bruni.daily.idioms.data.database"
        private const val CLASS_NAME = "Database"
        private const val NAME = "$PACKAGE_NAME.$CLASS_NAME"
        private const val FILE_NAME = "Idioms"
        private const val VERSION = "1.0.2"

        @Volatile
        private var INSTANCE: Database? = null

        fun instance(context: Context): Database {
            synchronized(this) {
                return INSTANCE ?: run {
                    Room.databaseBuilder(
                        context.applicationContext,
                        Database::class.java,
                        "$NAME.$VERSION"
                    )
                        .createFromAsset("$FILE_NAME.db")
                        .addMigrations(MIGRATION_2_3)
                        .build()
                        .also {
                            INSTANCE = it
                        }
                }
            }
        }
    }
}
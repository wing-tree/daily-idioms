package com.wing.tree.bruni.daily.idioms.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

internal val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
                CREATE TABLE question (
                    `index` INTEGER PRIMARY KEY NOT NULL,
                    answer INTEGER,
                    correct_answer INTEGER NOT NULL,
                    is_solved INTEGER NOT NULL,
                    options TEXT NOT NULL,
                    text TEXT NOT NULL
                )
                """.trimIndent())
    }
}
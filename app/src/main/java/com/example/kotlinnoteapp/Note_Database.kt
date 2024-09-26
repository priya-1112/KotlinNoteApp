package com.example.kotlinnoteapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note_DataClass::class], version = 1)
abstract class Note_Database: RoomDatabase() {

    abstract fun notedao (): Note_Dao

    companion object {
        @Volatile

        private var INSTANCE: Note_Database?=  null

        fun getdatabase (context: Context): Note_Database{

            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        Note_Database::class.java,"note_database").build()
                }
            }
            return INSTANCE!!
        }

    }

}
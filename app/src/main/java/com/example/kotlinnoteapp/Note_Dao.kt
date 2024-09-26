package com.example.kotlinnoteapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface Note_Dao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert (note : Note_DataClass)

    @Update
    suspend fun update (note: Note_DataClass)

    @Delete
    suspend fun delete (note: Note_DataClass)

    @Query("SELECT * FROM Note_Table")
    fun getreadablenote (): LiveData<List<Note_DataClass>>

    @Query("SELECT * FROM Note_Table WHERE id =:Noteid")
    fun getnotebyid (Noteid : Int): Note_DataClass?


}
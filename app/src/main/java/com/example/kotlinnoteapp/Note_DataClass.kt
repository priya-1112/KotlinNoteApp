package com.example.kotlinnoteapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Note_Table")
data class Note_DataClass(
      @PrimaryKey (autoGenerate = true)
      val id: Int?= null,

      var title : String= "",
      var content :String= ""


)
package com.example.appnoteroomdb.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note (
    @PrimaryKey(autoGenerate = true) val  id: Int = 0,
    @ColumnInfo(name = "content") val content: String
)
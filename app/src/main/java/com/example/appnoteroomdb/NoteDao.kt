package com.example.appnoteroomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table ORDER BY id ASC")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM note_table WHERE content LIKE :content")
    suspend fun findNoteByContent(content: String): Note

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

}
package com.example.appnoteroomdb.data

import androidx.room.*
import com.example.appnoteroomdb.data.Note


@Dao
interface NoteDao {

    // sap xep id tang dan thi thay DESC = ASC
    @Query("SELECT * FROM note_table ORDER BY id DESC")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM note_table WHERE content LIKE :content")
    suspend fun findNoteByContent(content: String): Note

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)

}
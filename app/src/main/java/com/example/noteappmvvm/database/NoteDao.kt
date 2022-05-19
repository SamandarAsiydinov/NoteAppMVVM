package com.example.noteappmvvm.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.noteappmvvm.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE noteTitle LIKE :query OR noteBody LIKE :query ORDER BY id DESC")
    fun searchNote(query: String?): LiveData<List<Note>>
}
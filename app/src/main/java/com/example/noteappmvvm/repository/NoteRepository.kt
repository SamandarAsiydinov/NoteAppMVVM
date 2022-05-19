package com.example.noteappmvvm.repository

import com.example.noteappmvvm.database.NoteDatabase
import com.example.noteappmvvm.model.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun addNote(note: Note) = db.noteDao().addNote(note)

    suspend fun updateNote(note: Note) = db.noteDao().updateNote(note)

    suspend fun deleteNote(note: Note) = db.noteDao().deleteNote(note)

    fun getAllNotes() = db.noteDao().getAllNotes()

    fun searchNote(query: String?) = db.noteDao().searchNote(query)
}
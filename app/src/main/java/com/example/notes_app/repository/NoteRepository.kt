package com.example.notes_app.repository

import com.example.notes_app.database.NoteDatabase
import com.example.notes_app.model.Note

class NoteRepository(private val db: NoteDatabase) {
    suspend fun insertNote(note : Note) = db.getNoteDao().insertNote(note)
    suspend fun updateNote(note : Note) = db.getNoteDao().updateNote(note)
    suspend fun deleteNote(note : Note) = db.getNoteDao().deleteNote(note)

    fun getAllNotes() = db.getNoteDao()

    fun searchNote(query: String)= db.getNoteDao().searchNote(query)

}
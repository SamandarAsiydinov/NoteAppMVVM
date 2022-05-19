package com.example.noteappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteappmvvm.repository.NoteRepository

@Suppress("UNCHECKED_CAST")
class NoteViewModelProviderFactory(
    private val application: Application,
    private val noteRepository: NoteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(application, noteRepository) as T
    }
}
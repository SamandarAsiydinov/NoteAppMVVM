package com.example.noteappmvvm.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.noteappmvvm.R
import com.example.noteappmvvm.database.NoteDatabase
import com.example.noteappmvvm.databinding.ActivityMainBinding
import com.example.noteappmvvm.repository.NoteRepository
import com.example.noteappmvvm.viewmodel.NoteViewModel
import com.example.noteappmvvm.viewmodel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)
        setupViewModel()
    }

    private fun setupViewModel() {
        val noteRepository = NoteRepository(
            NoteDatabase(this)
        )
        val viewModelProviderFactory = NoteViewModelProviderFactory(
            application,
            noteRepository
        )
        viewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        )[NoteViewModel::class.java]
    }
}
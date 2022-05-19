package com.example.noteappmvvm.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.noteappmvvm.R
import com.example.noteappmvvm.activity.MainActivity
import com.example.noteappmvvm.databinding.FragmentNewNoteBinding
import com.example.noteappmvvm.model.Note
import com.example.noteappmvvm.util.snackBar
import com.example.noteappmvvm.viewmodel.NoteViewModel

class NewNoteFragment : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var mView: View

    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mView = view
        viewModel = (activity as MainActivity).viewModel
        initViews(view)
    }

    private fun initViews(view: View) {

    }

    private fun saveNote(view: View) {
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty() && noteBody.isNotEmpty()) {
            val note = Note(0, noteTitle, noteBody)
            viewModel.addNote(note)
            snackBar(view, "Note saved successfully")
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        } else {
            snackBar(view, "Please enter note!")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_new_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun isNotEmpty(s1: String, s2: String): Boolean {
        return !(TextUtils.isEmpty(s1) && TextUtils.isEmpty(s2))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
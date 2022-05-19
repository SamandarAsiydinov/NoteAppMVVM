package com.example.noteappmvvm.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteappmvvm.R
import com.example.noteappmvvm.activity.MainActivity
import com.example.noteappmvvm.databinding.FragmentUpdateNoteBinding
import com.example.noteappmvvm.model.Note
import com.example.noteappmvvm.util.snackBar
import com.example.noteappmvvm.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment(){

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var viewModel: NoteViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentNote = args.note!!
        viewModel = (activity as MainActivity).viewModel
        mView = view
        initViews(view)
    }

    private fun initViews(view: View) {
        binding.apply {
            etNoteBodyUpdate.setText(currentNote.noteBody)
            etNoteTitleUpdate.setText(currentNote.noteTitle)

            fabDone.setOnClickListener {
                updateNote(view)
            }
        }
    }

    private fun updateNote(view: View) {
        val noteTitle = binding.etNoteTitleUpdate.text.toString().trim()
        val noteBody = binding.etNoteBodyUpdate.text.toString().trim()

        if (noteTitle.isNotEmpty() && noteBody.isNotEmpty()) {
            val note = Note(currentNote.id, noteTitle, noteBody)
            viewModel.updateNote(note)
            snackBar(view, "Note updated")
            view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
        } else {
            snackBar(view, "Please enter note")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteNote()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Delete Note")
            setMessage("Do you want to delete this note?")
            setPositiveButton("Delete") { _, _ ->
                viewModel.deleteNote(currentNote)
                mView.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
                snackBar(mView, "Successfully deleted note")
            }
            setNeutralButton("Cancel", null)
        }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
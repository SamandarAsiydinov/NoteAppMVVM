package com.example.noteappmvvm.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteappmvvm.R
import com.example.noteappmvvm.activity.MainActivity
import com.example.noteappmvvm.adapter.NoteAdapter
import com.example.noteappmvvm.databinding.FragmentHomeBinding
import com.example.noteappmvvm.viewmodel.NoteViewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteAdapter: NoteAdapter
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        initViews(view)
    }

    private fun initViews(view: View) {
        noteAdapter = NoteAdapter()
        setupRv()
        setupViewModel()
        binding.floatingBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
        noteAdapter.onItemClick = {
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(it)
            view.findNavController().navigate(direction)
        }
    }

    private fun setupRv() = binding.recyclerView.apply {
        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        setHasFixedSize(true)
        adapter = noteAdapter
    }

    private fun setupViewModel() {
        activity?.let {
            viewModel.getAllNotes().observe(viewLifecycleOwner) { list ->
                noteAdapter.submitList(list)

                binding.apply {
                    recyclerView.isVisible = list.isNotEmpty()
                    imgIsEmpty.isVisible = list.isEmpty()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        val menuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = true
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNotes(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNotes(newText)
        }
        return true
    }

    private fun searchNotes(query: String?) {
        val searchQuery = "%$query%"
        viewModel.searchNote(searchQuery).observe(this) { list ->
            noteAdapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
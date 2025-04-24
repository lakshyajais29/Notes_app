package com.example.notes_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes_app.MainActivity
import com.example.notes_app.R
import com.example.notes_app.adapter.NoteAdapter
import com.example.notes_app.databinding.FragmentEditNoteBinding
import com.example.notes_app.model.Note


class EditNoteFragment : Fragment(R.layout.fragment_edit_note), MenuProvider {

    private var editNoteFragment: FragmentEditNoteBinding? = null
    private val binding get() = editNoteFragment!!

    private lateinit var notesViewHolder: NoteAdapter.NoteViewHolder
    private lateinit var currentnote: Note

    private val args: EditNoteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var editNoteBinding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.RESUMED)

        var notesViewModel = (activity as MainActivity).noteViewModel

        currentnote = args.note!!

        binding.editNoteTitle.setText(currentnote.noteTitle)
        binding.editNoteDesc.setText(currentnote.noteDesc)

        binding.editNoteFab.setOnClickListener{
            val noteTitle = binding.editNoteTitle.text.toString().trim()
            val noteDesc = binding.editNoteDesc.text.toString().trim()


            if(noteTitle.isNotEmpty()){
                val note = Note(currentnote.id, noteTitle, noteDesc)
                notesViewModel.updateNote(note)
                view.findNavController().popBackStack(R.id.homeFragment,false)
            }else{
                Toast.makeText(requireContext(),"Enter note title",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun deleteNote(){
        AlertDialog.Builder(requireActivity()).apply{
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete this note?")
            setPositiveButton("Delete"){_,_ ->
                notesViewHolder.deleteNote(currentnote)
                Toast.makeText(requireContext(), "Note deleted successfully", Toast.LENGTH_SHORT).show()

                view?.findNavController()?.popBackStack(R.id.homeFragment,false)

            }
            setNegativeButton("Cancel",null)

        }.create().show()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.menu_edit,menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when(menuItem.itemId){
            R.id.deleteMenu -> {
                deleteNote()
                true
            }
            else -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        var editNoteBinding = null
    }

}
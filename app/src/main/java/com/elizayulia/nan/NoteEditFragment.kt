package com.elizayulia.nan

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.FieldPosition

class NoteEditFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val ARGS_NAME = "args_name"
        private const val NOTE_ID_EXTRA = "HABIT_ID_EXTRA"
        private const val IS_NEW_NOTE_EXTRA = "IS_NEW_HABIT_EXTRA"

        fun newInstance(name: String, noteId: Int? = null, isNewNote: Boolean=true) : NoteEditFragment {
            val fragment = NoteEditFragment()
            val bundle = Bundle()
            bundle.putString(name, ARGS_NAME)
            bundle.putBoolean(IS_NEW_NOTE_EXTRA, isNewNote)
            if (!isNewNote && noteId != null) {
                bundle.putInt(NOTE_ID_EXTRA, noteId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    var layout: View? = null
    var callback: NoteEditFragment.NoteEditor? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as NoteEditFragment.NoteEditor
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(
            R.layout.fragment_note_edit,
            container,
            false
        )

        val fab = layout!!.findViewById<FloatingActionButton>(R.id.fab_done)
        fab.setOnClickListener(this)

        if (!arguments?.getBoolean(IS_NEW_NOTE_EXTRA)!!) {
            arguments?.getInt(NOTE_ID_EXTRA)?.let { loadNote(it) }
        }

        return layout
    }

    private fun loadNote(position: Int) {
        val note = Notes.notes[position]
        val name = layout!!.findViewById<EditText>(R.id.note_name)
        name.setText(note.name)

        val description = layout!!.findViewById<EditText>(R.id.note_description)
        description.setText(note.description)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_done -> onClickDone()
        }
    }

    private fun onClickDone() {
        val name = if (layout?.findViewById<EditText>(R.id.note_name)?.text.toString() != "") {
            layout?.findViewById<EditText>(R.id.note_name)?.text.toString()
        }
        else {
            showToast("Name is empty")
            return
        }
        val description = layout?.findViewById<EditText>(R.id.note_description)?.text.toString()

        // добавляем в список заметок
//        Notes.notes.add(
//            Note(name, description)
//        )

        val note = Note(name, description)
        if (!arguments?.getBoolean(IS_NEW_NOTE_EXTRA)!!) {
            arguments?.getInt(NOTE_ID_EXTRA)?.let {
                Notes.notes.removeAt(it)
                Notes.notes.add(it, note)
            }
        }
        else {
            Notes.notes.add(note)
        }

        showToast("Note saved")

        callback?.onClickNoteDone()
    }


    private fun showToast(message: String) {
        val toast = Toast.makeText(
            activity,
            message,
            Toast.LENGTH_SHORT
        )
        toast.show()
    }

    interface NoteEditor {
        fun onClickNoteDone()
    }
}

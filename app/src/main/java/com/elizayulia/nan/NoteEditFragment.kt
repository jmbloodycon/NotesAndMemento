package com.elizayulia.nan

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NoteEditFragment : Fragment(),
    View.OnClickListener {

    companion object {  // тут лежит всё статическое
        private const val ARGS_NAME = "args_name"
        private const val NOTE_ID_EXTRA = "NOTE_ID_EXTRA"
        private const val IS_NEW_NOTE_EXTRA = "IS_NEW_NOTE_EXTRA"

        // для создания нового экземпляра фрагмента
        fun newInstance(name: String, noteId: Int? = null, isNewNote: Boolean = true): NoteEditFragment {
            val fragment = NoteEditFragment()
            val bundle = Bundle()
            bundle.putString(
                name,
                ARGS_NAME
            )
            bundle.putBoolean(
                IS_NEW_NOTE_EXTRA,
                isNewNote
            )
            if (!isNewNote && noteId != null) {
                bundle.putInt(
                    NOTE_ID_EXTRA,
                    noteId
                )
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    var layout: View? = null        // сохраняем макет, чтобы можно было в нем искать элементы
    var callback: NoteEditFragment.NoteEditor? =
        null   // чтобы общаться с Activity сохраняем её как экземпляр интерфейса с методоми для общения

    override fun onAttach(context: Context) {   // метод жизненного цикла, вызывается, когда фрагмент связывается с Activity
        super.onAttach(context)
        callback =
            activity as NoteEditFragment.NoteEditor      // сохраняем активность, чтобы работать с методами активности, указанными в интерфесе внизу класса
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {             // метод жизненного цикла
        // Привязка лайаута к фрагменту
        layout = inflater.inflate(
            R.layout.fragment_note_edit,
            container,
            false
        )

        val fab = layout!!.findViewById<FloatingActionButton>(R.id.fab_done)    // нашли FAB
        fab.setOnClickListener(this)        // привязали слушателя нажатий

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

    // метод интерфейса View.OnClickListener, чтобы мы могда слушать и обрабатывать нажатия
    override fun onClick(v: View?) {
        when (v?.id) {                      // аналог switch-case
            R.id.fab_done -> onClickDone()  // если нажали на FAB, вызываем метод
        }
    }

    private fun onClickDone() {
        // проверяем, написано ли что-нибодь в поле Name. Если да, сохраняем в переменную
        val name = if (layout?.findViewById<EditText>(R.id.note_name)?.text.toString() != "") {
            layout?.findViewById<EditText>(R.id.note_name)?.text.toString()
        }
        else {
            showToast("Name is empty")  // показываем посказку
            return
        }
        // сохраняем описание
        val description = layout?.findViewById<EditText>(R.id.note_description)?.text.toString()

        val note = Note(
            name,
            description
        )
        if (!arguments?.getBoolean(IS_NEW_NOTE_EXTRA)!!) {
            arguments?.getInt(NOTE_ID_EXTRA)?.let {
                Notes.notes.removeAt(it)
                Notes.notes.add(
                    it,
                    note
                )
                dbUpdateNote(
                    Notes.notes[it],
                    it
                )
            }
        }
        else {
            val add = Notes.notes.add(note)
            dbInsertNote(note)
        }

        showToast("Note saved")     // показываем посказку

        callback?.onClickNoteDone()     // вызываем соответствующий метод активности, чтобы вернутся на оснофной фрагмент
    }

    private fun dbInsertNote(note: Note) {
        val dbHelper = NaNDBHelper(requireContext())
        val db = dbHelper.writableDatabase

        val noteValues = ContentValues()
        noteValues.put(
            "NAME",
            note.name
        )
        noteValues.put(
            "DESCRIPTION",
            note.description
        )

        db.insert(
            "NOTES",
            null,
            noteValues
        )
        db.close()
    }

    private fun dbUpdateNote(note: Note, position: Int) {
        val dbHelper = NaNDBHelper(requireContext())
        val db = dbHelper.writableDatabase

        val noteValues = ContentValues()
        noteValues.put(
            "NAME",
            note.name
        )
        noteValues.put(
            "DESCRIPTION",
            note.description
        )

        db.update(
            "NOTES",
            noteValues,
            "_id = ?",
            arrayOf((position+1).toString())
        )
        db.close()
    }

    // метод для показа подсказок
    private fun showToast(message: String) {
        // создание подсказки
        val toast = Toast.makeText(
            activity,
            message,
            Toast.LENGTH_SHORT
        )
        toast.show()        // показываем подсказку
    }

    // интерфейс для взаимодействия с активностью
    interface NoteEditor {

        fun onClickNoteDone()
    }
}

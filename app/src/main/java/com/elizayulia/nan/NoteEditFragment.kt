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

class NoteEditFragment : Fragment(), View.OnClickListener {

    companion object {  // тут лежит всё статическое
        private const val ARGS_NAME = "args_name"

        // для создания нового экземпляра фрагмента
        fun newInstance(name: String) : NoteEditFragment {
            val fragment = NoteEditFragment()
            val bundle = Bundle()
            bundle.putString(name, ARGS_NAME)
            fragment.arguments = bundle
            return fragment
        }
    }

    var layout: View? = null        // сохраняем макет, чтобы можно было в нем искать элементы
    var callback: NoteEditFragment.NoteEditor? = null   // чтобы общаться с Activity сохраняем её как экземпляр интерфейса с методоми для общения

    override fun onAttach(context: Context) {   // метод жизненного цикла, вызывается, когда фрагмент связывается с Activity
        super.onAttach(context)
        callback = activity as NoteEditFragment.NoteEditor      // сохраняем активность, чтобы работать с методами активности, указанными в интерфесе внизу класса
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {             // метод жизненного цикла
        layout =  inflater.inflate(
            R.layout.fragment_note_edit,
            container,
            false
        )

        val fab = layout!!.findViewById<FloatingActionButton>(R.id.fab_done)
        fab.setOnClickListener(this)

        return layout
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

        Notes.notes.add(
            Note(name, description)
        )

        showToast("Note saved")

        callback?.onClickNoteDone()
    }


    private fun showToast(message: String) {
        // создание подсказки
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

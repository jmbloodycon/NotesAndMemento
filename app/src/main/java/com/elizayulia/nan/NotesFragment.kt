package com.elizayulia.nan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

// основной фрагмент, в котором список
class NotesFragment
    : Fragment(),
    View.OnClickListener { // чтобы мы могли ловить нажатия на кнопки

    companion object {  // тут лежит всё статическое
        private const val ARGS_NAME = "args_name"

        // для создания нового экземпляра фрагмента
        fun newInstance(name: String) : NotesFragment {
            val fragment = NotesFragment()
            val bundle = Bundle()
            bundle.putString(name, ARGS_NAME)
            fragment.arguments = bundle
            return fragment
        }
    }

    var name: String = ""
    var callback: NoteNotificationManager? = null   // чтобы общаться с Activity сохраняем её как экземпляр интерфейса с методоми для общения
    var layout: View? = null    // сохраняем макет, чтобы можно было в нем искать элементы

    private lateinit var recyclerView: RecyclerView             // списковое представление
    private lateinit var viewAdapter: RecyclerView.Adapter<*>   // адаптер для спиского представления (чтобы преобразовывать наши данные в элементы, отображаемые на экране)
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onAttach(context: Context) {   // метод жизненного цикла, вызывается, когда фрагмент связывается с Activity
        super.onAttach(context)
        callback = activity as NoteNotificationManager   // сохраняем активность, чтобы работать с методами активности, указанными в интерфесе внизу класса
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {             // метод жизненного цикла
        // Привязка лайаута к фрагменту
        layout = inflater.inflate(
            R.layout.fragment_notes,
            container,
            false
        )

        val fab = layout!!.findViewById<FloatingActionButton>(R.id.fabAdd)  // нашли FAB
        fab.setOnClickListener(this)                                                    // привязали слушателя нажатий

        return layout
    }

    override fun onStart() {        // метод жизненного цикла. Вызывается, когда фрагмент становится видимым
        super.onStart()

        viewManager = LinearLayoutManager(activity)     // привязываем менеджер
        viewAdapter = NoteAdapter(Notes.notes).also {   // и адаптер
            it.listener = object : NoteAdapter.Listener {
                override fun onClick(position: Int) {
                    val intent = Intent(activity, EditActivity::class.java)
                    intent.putExtra(EditActivity.EXTRA_TYPE, EditActivity.EXTRA_TYPE_NOTE)
                    intent.putExtra(EditActivity.EXTRA_IS_NEW, false)
                    intent.putExtra(EditActivity.EXTRA_POSITION, position)
                    startActivity(intent)

//                    val transaction = activity?.supportFragmentManager?.beginTransaction()     // создание транзакцию
//                    val fragment = NoteEditFragment.newInstance("new_note", isNewNote = false, noteId = position)     // создание экземпляра фрагмента
//                    transaction!!
//                        .replace(R.id.fragment_container, fragment)     // заменяем фрагмент на нужный
//                        .addToBackStack("Note_fragment")        // добавляем транзакцию в стек транзакций
//                        .commit()       // выполняем транзакцию
                }
            }
        }

        // создаем списковое представление
        recyclerView = layout!!.findViewById<RecyclerView>(R.id.note_recycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    // метод интерфейса View.OnClickListener, чтобы мы могда слушать и обрабатывать нажатия
    override fun onClick(v: View?) {
        when (v?.id) {                  // аналог switch-case
            R.id.fabAdd -> onAddClick() // если нажали на FAB, вызываем метод
        }
    }

    private fun onAddClick(){
        callback?.onAddNoteClicked()    // вызываем соответствующий метод активности, чтобы вызвать другой фрагмент
    }
}

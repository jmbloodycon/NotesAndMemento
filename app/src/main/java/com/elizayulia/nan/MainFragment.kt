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

class MainFragment
    : Fragment(),
    View.OnClickListener {

    companion object {
        private const val ARGS_NAME = "args_name"

        fun newInstance(name: String) : MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putString(name, ARGS_NAME)
            fragment.arguments = bundle
            return fragment
        }
    }

    var name: String = ""
    var callback: NoteNotificationManger? = null
    var layout: View? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as NoteNotificationManger
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layout = inflater.inflate(
            R.layout.fragment_main,
            container,
            false
        )

        val fab = layout!!.findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener(this)

        return layout
    }

    override fun onStart() {
        super.onStart()

        viewManager = LinearLayoutManager(activity)
        viewAdapter = NoteAdapter(Notes.notes)

        recyclerView = layout!!.findViewById<RecyclerView>(R.id.note_recycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fabAdd -> onAddClick()
        }
    }

    private fun onAddClick(){
        callback?.onAddNoteClicked()
    }

    interface NoteNotificationManger {
        fun onAddNoteClicked()
//        fun onAddNotificationClicked()        метод для вызова экрана создания/редактирования напоминания (его пока нет)
    }
}

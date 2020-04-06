package com.elizayulia.nan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), MainFragment.NoteNotificationManger, NoteEditFragment.NoteEditor {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = MainFragment.newInstance("main_fragment")
            transaction
                .add(R.id.fragment_container, fragment, "main_fragment_tag")
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onAddNoteClicked() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = NoteEditFragment.newInstance("new_note")
        transaction
            .replace(R.id.fragment_container, fragment)
            .addToBackStack("Note_fragment")
            .commit()
    }

    override fun onClickNoteDone() {
        val transaction = supportFragmentManager.popBackStack()
    }
}

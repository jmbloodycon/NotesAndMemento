package com.elizayulia.nan

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity(), NotesFragment.NoteNotificationManger, NoteEditFragment.NoteEditor {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = NotesFragment.newInstance("main_fragment")
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

package com.elizayulia.nan

import android.content.Context
import android.content.Intent
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

class MainActivity : AppCompatActivity(), NoteNotificationManager {

    override fun onCreate(savedInstanceState: Bundle?) { // метод жизненного цикла
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadDB()
//        val viewPager = findViewById<ViewPager>(R.id.pager)
//        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)

        // если приложение только запустилось мы открваем транзакцию, при помощи которой отображаем фрагмент
        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction() // создание транзакцию
            val fragment = NotesFragment.newInstance("main_fragment") // создание экземпляра фрагмента
            transaction
                .add(R.id.fragment_container, fragment, "main_fragment_tag")    // добавление фрагмента, который хотим отобразить
                .addToBackStack(null)   // добавляем транзакцию в стек транзакций
                .commit()   // выполняем транзакцию
        }
    }

    // вызывается, когда нажали на кнопку "добавить запись"
    override fun onAddNoteClicked() {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(EditActivity.EXTRA_TYPE, EditActivity.EXTRA_TYPE_NOTE)
        intent.putExtra(EditActivity.EXTRA_IS_NEW, true)
        startActivity(intent)
    }

    override fun onAddNotificationClicked() {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(EditActivity.EXTRA_TYPE, EditActivity.EXTRA_TYPE_NOTIFICATION)
        intent.putExtra(EditActivity.EXTRA_IS_NEW, true)
        startActivity(intent)
    }

    fun loadDB() {
        val dbHelper = NaNDBHelper(this)
        val db = dbHelper.readableDatabase
        val cursor = db.query("NOTES", arrayOf("NAME", "DESCRIPTION"),
                              null, null, null, null, null)
        val notesCount = cursor.count
        if (notesCount > 0) {
            cursor.moveToFirst()
            for (i in 1 .. notesCount) {
                val noteName = cursor.getString(0)
                val noteDescription = cursor.getString(1)
                Notes.notes.add(
                    Note(
                        noteName,
                        noteDescription
                    )
                )
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
    }
}

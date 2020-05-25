package com.elizayulia.nan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EditActivity : AppCompatActivity(),
    NoteEditFragment.NoteEditor, NotificationEditFragment.NotificationsEditor {

    companion object {
        const val EXTRA_TYPE = "type"
        const val EXTRA_TYPE_NOTE = "note"
        const val EXTRA_TYPE_NOTIFICATION = "notification"

        const val EXTRA_IS_NEW = "is_new"

        const val EXTRA_POSITION = "position"
    }

    private lateinit var extras: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        if (intent.extras != null) {
            extras = intent.extras!!
        }

        if (savedInstanceState == null) {
            val type = extras.getString(EXTRA_TYPE)
            if (type == EXTRA_TYPE_NOTE)
                openNoteEditor()
            else if (type == EXTRA_TYPE_NOTIFICATION)
                openNotificationEditor()
        }
    }

    fun openNoteEditor() {
        val transaction = supportFragmentManager.beginTransaction() // создание транзакцию
        val fragment = if (extras.getBoolean(EXTRA_IS_NEW)) {
            NoteEditFragment.newInstance(
                "note_edit_fragment"
            )
        } else {
            NoteEditFragment.newInstance(
                "note_edit_fragment",
                isNewNote = false,
                noteId = extras.getInt(EXTRA_POSITION)
            )
        }
        transaction
            .add(R.id.fragment_container, fragment, "note_fragment_tag")    // добавление фрагмента, который хотим отобразить
            .addToBackStack(null)   // добавляем транзакцию в стек транзакций
            .commit()
    }

    fun openNotificationEditor() {
        val transaction = supportFragmentManager.beginTransaction() // создание транзакцию
        val fragment = if (extras.getBoolean(EXTRA_IS_NEW)) {
            NotificationEditFragment.newInstance(
                "note_edit_fragment"
            )
        } else {
            NotificationEditFragment.newInstance(
                "note_edit_fragment",
                isNewNotification = false,
                notificationId = extras.getInt(EXTRA_POSITION)
            )
        }
        transaction
            .add(R.id.fragment_container, fragment, "notification_fragment_tag")    // добавление фрагмента, который хотим отобразить
            .addToBackStack(null)   // добавляем транзакцию в стек транзакций
            .commit()
    }

    override fun onClickNoteDone() {
        finish()
    }

    override fun onClickNotificationDone() {
        finish()
    }
}

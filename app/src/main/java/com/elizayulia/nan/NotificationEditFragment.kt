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

/**
 * A simple [Fragment] subclass.
 */
class NotificationEditFragment : Fragment(), View.OnClickListener {

    companion object {  // тут лежит всё статическое
        private const val ARGS_NAME = "args_name"
        private const val NOTIFICATION_ID_EXTRA = "NOTIFICATION_ID_EXTRA"
        private const val IS_NEW_NOTIFICATION_EXTRA = "IS_NEW_NOTIFICATION_EXTRA"

        // для создания нового экземпляра фрагмента
        fun newInstance(name: String, notificationId: Int? = null, isNewNotification: Boolean=true)
                : NotificationEditFragment {
            val fragment = NotificationEditFragment()
            val bundle = Bundle()
            bundle.putString(name, ARGS_NAME)
            bundle.putBoolean(IS_NEW_NOTIFICATION_EXTRA, isNewNotification)
            if (!isNewNotification && notificationId != null) {
                bundle.putInt(NOTIFICATION_ID_EXTRA, notificationId)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    var layout: View? = null        // сохраняем макет, чтобы можно было в нем искать элементы
    var callback: NotificationEditFragment.NotificationsEditor? = null   // чтобы общаться с Activity сохраняем её как экземпляр интерфейса с методоми для общения

    override fun onAttach(context: Context) {   // метод жизненного цикла, вызывается, когда фрагмент связывается с Activity
        super.onAttach(context)
        callback = activity as NotificationEditFragment.NotificationsEditor      // сохраняем активность, чтобы работать с методами активности, указанными в интерфесе внизу класса
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

        if (!arguments?.getBoolean(IS_NEW_NOTIFICATION_EXTRA)!!) {
            arguments?.getInt(NOTIFICATION_ID_EXTRA)?.let { loadNotification(it) }
        }

        return layout
    }

    private fun loadNotification(position: Int) {
        val notification = Notifications.notifications[position]
        val name = layout!!.findViewById<EditText>(R.id.notification_name)
        name.setText(notification.name)

        val description = layout!!.findViewById<EditText>(R.id.notification_description)
        description.setText(notification.description)
    }

    // метод интерфейса View.OnClickListener, чтобы мы могда слушать и обрабатывать нажатия
    override fun onClick(v: View?) {
        when (v?.id) {                      // аналог switch-case
            R.id.fab_done -> onClickDone()  // если нажали на FAB, вызываем метод
        }
    }

    private fun onClickDone() {
        // проверяем, написано ли что-нибодь в поле Name. Если да, сохраняем в переменную
        val name = if (layout?.findViewById<EditText>(R.id.notification_name)?.text.toString() != "") {
            layout?.findViewById<EditText>(R.id.notification_name)?.text.toString()
        }
        else {
            showToast("Name is empty")  // показываем посказку
            return
        }
        // сохраняем описание
        val description = layout?.findViewById<EditText>(R.id.notification_description)?.text.toString()

        val notification = Notification(name, description)
        if (!arguments?.getBoolean(IS_NEW_NOTIFICATION_EXTRA)!!) {
            arguments?.getInt(NOTIFICATION_ID_EXTRA)?.let {
                Notifications.notifications.removeAt(it)
                Notifications.notifications.add(it, notification)
            }
        }
        else {
            Notifications.notifications.add(notification)
        }

        showToast("Notification saved")     // показываем посказку

        callback?.onClickNotificationDone()     // вызываем соответствующий метод активности, чтобы вернутся на оснофной фрагмент
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
    interface NotificationsEditor {
        fun onClickNotificationDone()
    }
}

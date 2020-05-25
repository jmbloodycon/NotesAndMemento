package com.elizayulia.nan

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class NotificationAdapter(private val notifications: MutableList<Notification>)
    : RecyclerView.Adapter<NotificationAdapter.NotificationsViewHolder>() {

    lateinit var listener: Listener

    interface Listener {
        fun onClick(position: Int)
    }

    class NotificationsViewHolder(val cardView: MaterialCardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_note, parent, false) as MaterialCardView
        return NotificationsViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: NotificationsViewHolder, position: Int) {
        val notificationName = holder.cardView.findViewById<TextView>(R.id.notification_name)
        notificationName.text = notifications[position].name
        val noteDescription = holder.cardView.findViewById<TextView>(R.id.notification_description)
        noteDescription.text = notifications[position].description

        holder.cardView.setOnClickListener { listener.onClick(position) }
    }

    override fun getItemCount(): Int = notifications.size
}
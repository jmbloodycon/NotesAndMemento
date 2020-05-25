package com.elizayulia.nan

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

// адаптер для RecyclerView, чтобы он мог отображать наши данные
class NoteAdapter(private val notes: MutableList<Note>)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){

    lateinit var listener: Listener

    interface Listener {
        fun onClick(position: Int)
    }

    class NoteViewHolder(val cardView: MaterialCardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_note, parent, false) as MaterialCardView
        return NoteViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val noteName = holder.cardView.findViewById<TextView>(R.id.note_name)
        noteName.text = notes[position].name
        val noteDescription = holder.cardView.findViewById<TextView>(R.id.note_description)
        noteDescription.text = notes[position].description

        holder.cardView.setOnClickListener { listener.onClick(position) }
    }

    override fun getItemCount(): Int = notes.size
}

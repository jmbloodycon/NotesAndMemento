package com.elizayulia.nan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> NotesFragment.newInstance("notes")
            else -> NotificationFragment.newInstance("notifications")
        }
    }

}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
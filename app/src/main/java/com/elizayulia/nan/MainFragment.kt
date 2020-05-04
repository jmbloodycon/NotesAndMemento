package com.elizayulia.nan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    companion object {  // тут лежит всё статическое
        private const val ARGS_NAME = "args_name"

        // для создания нового экземпляра фрагмента
        fun newInstance(name: String) : MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putString(name, ARGS_NAME)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(
            R.layout.fragment_main,
            container,
            false
        )

        val viewPager = layout!!.findViewById<View>(R.id.pager) as ViewPager2
//        viewPager.adapter = activity?.let { MainFragment.CustomPagerAdapter(it) }
        viewPager.adapter = CustomPagerAdapter(this)

        return layout
    }

    private class CustomPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> NotesFragment.newInstance("notes")
                1 -> NotificationFragment.newInstance("notifications")
                else -> NotificationFragment.newInstance("notifications")
            }
        }

        override fun getItemCount(): Int = 2
    }
}

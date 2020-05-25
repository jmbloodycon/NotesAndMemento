package com.elizayulia.nan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
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

    var layout: View? = null
    var viewPager: ViewPager2? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        layout = inflater.inflate(
            R.layout.fragment_main,
            container,
            false
        )

        return layout
    }


}

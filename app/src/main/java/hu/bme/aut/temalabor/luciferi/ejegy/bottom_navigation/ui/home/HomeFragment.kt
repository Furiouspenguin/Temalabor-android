package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.adapter.HomePagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabTitles = listOf<String>(getString(R.string.id), getString(R.string.valid), getString(
                    R.string.other))
        home_pager.adapter = HomePagerAdapter(tabTitles,childFragmentManager)
        tabs.setupWithViewPager(home_pager)
    }
}
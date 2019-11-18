package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.idpager.IDPagerFragment
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.otherpager.OtherPagerFragment
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager.ValidTicketsPagerFragment
import java.lang.Exception
import java.lang.IllegalArgumentException

class HomePagerAdapter(private val tabTitles : List<String>,manager : FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    init {
        if (tabTitles.size != 3) throw Exception("Not enough titles!")
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> IDPagerFragment()
            1 -> ValidTicketsPagerFragment()
            2 -> OtherPagerFragment()
            else -> throw IllegalArgumentException("No such page!")
        }
    }

    override fun getCount(): Int = tabTitles.size

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

}
package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.IDPagerFragment
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.OtherPager
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.ValidTicketsPagerFragment
import java.lang.IllegalArgumentException

class HomePagerAdapter(manager : FragmentManager) : FragmentStatePagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    companion object {
        private const val NUM_PAGES = 3
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> IDPagerFragment()
            1 -> ValidTicketsPagerFragment()
            2 -> OtherPager()
            else -> throw IllegalArgumentException("No such page!")
        }
    }

    override fun getCount(): Int = NUM_PAGES

    private val tabTitles = listOf<String>("ID", "Valid", "Other")

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

}
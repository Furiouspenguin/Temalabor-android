package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.temalabor.luciferi.ejegy.R

class ValidTicketsPagerFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager_validtickets,container,false)
    }

    //TODO: userTickets itt (RECYCLEVIEW!!!)
}
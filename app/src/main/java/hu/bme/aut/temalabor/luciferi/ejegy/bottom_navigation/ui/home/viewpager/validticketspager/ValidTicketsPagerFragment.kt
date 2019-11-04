package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.idpager.IDPagerViewModel
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.fragment_pager_validtickets.*
import org.jetbrains.anko.support.v4.longToast

class ValidTicketsPagerFragment : Fragment(){

    private lateinit var validTicketsPagerViewModel: ValidTicketsPagerViewModel
    private var tickets = ArrayList<UserTicket>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        validTicketsPagerViewModel = ViewModelProviders.of(this).get(ValidTicketsPagerViewModel::class.java)

        validTicketsPagerViewModel.tickets?.observe(this, Observer {
            this.tickets=ArrayList(it)
        })

        val root =  inflater.inflate(R.layout.fragment_pager_validtickets,container,false)



        return root
    }

    //TODO: userTickets itt (RECYCLEVIEW!!!)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

        val adapter = UserTicketsRecycleAdapter()

        recyclerview.adapter = adapter
        if (tickets.isEmpty()) longToast("Empty list of tickets")
        tickets.forEach{
            adapter.addTicket(it)
        }

        RetrofitClient.MyAsyncGetUserTickets(RestApiRepository.getUserData().value!!.id){
            it.forEach {
                adapter.addTicket(it)
            }
            RestApiRepository.setUserTickets(it)
        }.execute()
    }
}
package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.otherpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager.UserTicketActivity
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager.adapter.UserTicketsRecycleAdapter
import kotlinx.android.synthetic.main.fragment_pager_other.*
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class OtherPagerFragment : Fragment(){

    private lateinit var otherPagerViewModel: OtherPagerViewModel
    private var tickets = ArrayList<UserTicket>()

    private var invalidTickets = ArrayList<UserTicket>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager_other,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        otherPagerViewModel = ViewModelProviders.of(this).get(OtherPagerViewModel::class.java)



        invalid_recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)

        val adapter =
            UserTicketsRecycleAdapter()

        invalid_recyclerview.adapter = adapter

        otherPagerViewModel.tickets?.observe(this, Observer {usertickets->

            tickets=ArrayList(usertickets)
            tickets.forEach {
                if (it.validFrom.isNullOrEmpty() or it.validUntil.isNullOrEmpty()) {
                    invalidTickets.add(it)
                    adapter.addTicket(it)
                }
            }
            adapter.notifyDataSetChanged()
        })
/*
        if (tickets.isEmpty()){
            RetrofitClient.MyAsyncGetUserTickets(RestApiRepository.getUserData().value!!.id){
                tickets = ArrayList(it)
                //tickets.sortWith(compareBy { it.validUntil })
                tickets.forEach {
                    adapter.addTicket(it)
                }
                RestApiRepository.setUserTickets(it)
            }.execute()
        }*/


        adapter.setOnItemCickListener(object : UserTicketsRecycleAdapter.OnItemClickListener{
            override fun onItemClick(ticketPosition: Int) {
                startActivity<UserTicketActivity>(
                    "position" to ticketPosition,
                    "valid" to false
                )
            }
        })
    }
}
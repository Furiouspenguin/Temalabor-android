package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.Line
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.TicketType
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager.adapter.UserTicketsRecycleAdapter
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.fragment_pager_validtickets.*
import org.jetbrains.anko.support.v4.startActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ValidTicketsPagerFragment : Fragment(){

    private var tickets = ArrayList<UserTicket>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager_validtickets,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        val adapter = UserTicketsRecycleAdapter(context!!)
        recyclerview.adapter = adapter

        RestApiRepository.liveValidUserTickets.observe(this, Observer {usertickets->
            if (usertickets.isEmpty()) {
                RetrofitClient.MyAsyncGetUserTickets(RestApiRepository.getUserData().value!!.id){
                    val validUserTickets = ArrayList<UserTicket>()
                    it.forEach { ticket->
                        val now = Calendar.getInstance().time
                        if (!ticket.validFrom.isNullOrEmpty() or !ticket.validUntil.isNullOrEmpty()) {
                            val df = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN)
                            val validFromDate = df.parse(ticket.validFrom.subSequence(0,10).toString())

                            val isDue =  (0 > validFromDate.compareTo(now))
                            if (isDue) {
                                validUserTickets.add(ticket)
                            }
                        }
                    }
                    validUserTickets.forEach {
                        adapter.addTicket(it)
                    }
                    adapter.notifyDataSetChanged()
                }.execute()
            } else {
                tickets=ArrayList(usertickets)
                tickets.forEach {
                    adapter.addTicket(it)
                }
                adapter.notifyDataSetChanged()
            }
        })

        adapter.setOnItemCickListener(object : UserTicketsRecycleAdapter.OnItemClickListener{
            override fun onItemClick(ticketPosition: Int) {
                startActivity<UserTicketActivity>(
                    "position" to ticketPosition,
                    "valid" to true
                )
            }
        })
    }
}
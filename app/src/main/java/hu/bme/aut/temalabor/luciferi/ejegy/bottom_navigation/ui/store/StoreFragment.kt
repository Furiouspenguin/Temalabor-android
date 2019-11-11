package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.TicketTypeWithPrice
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store.adapter.StoreRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_store.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast

class StoreFragment : Fragment() {

    private lateinit var storeViewModel: StoreViewModel
    private var tickets = ArrayList<TicketTypeWithPrice>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        storeViewModel = ViewModelProviders.of(this).get(StoreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_store, container, false)
        val textView: TextView = root.findViewById(R.id.text_favourites)
        storeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    //TODO: a tickets, a tickets/buy és a tickets/validate kell itt

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview_tickets.layoutManager =LinearLayoutManager(context).apply { orientation = RecyclerView.HORIZONTAL }
        recyclerview_tickets.setHasFixedSize(true)

        val adapter = StoreRecyclerAdapter(context!!)
        recyclerview_tickets.adapter = adapter

/*
        storeViewModel.tickets.observe(this, Observer {
            //adapter.submitList(it)
            tickets=ArrayList(it)
            tickets.forEach {
                adapter.addTicket(it)
            }
            adapter.notifyDataSetChanged()
            longToast("Got data")
        })
        adapter.setOnItemCickListener(object : StoreRecyclerAdapter.OnItemClickListener{
            override fun onItemClick(position : Int) {
                toast("item clicked")
            }
        })*/

        StoreViewModel.MyAsyncGetTicketTypesWithPrice {list->

            tickets=ArrayList(list!!)
            tickets.forEach { adapter.addTicket(it) }
            adapter.notifyDataSetChanged()

            adapter.setOnItemCickListener(object : StoreRecyclerAdapter.OnItemClickListener{
                override fun onItemClick(position : Int) {
                    val ticket : String = GsonBuilder().create().toJson(tickets[position])
                    toast("item clicked")
                    startActivity<BuyTicketActivity>(
                        "ticket" to ticket
                    )
                }
            })
        }.execute()

        pass_ticket_btn.setOnClickListener {
            val passTickets = ArrayList<TicketTypeWithPrice>()
            tickets.forEach {
                if (it.type == "passTicket") {
                    passTickets.add(it)
                }
            }
            val list : String = GsonBuilder().create().toJson(passTickets)
            startActivity<StoreCategoryActivity>(
                "title" to "Bérletek",
                "list" to list
            )
        }

        line_ticket_btn.setOnClickListener {
            val lineTickets = ArrayList<TicketTypeWithPrice>()
            tickets.forEach {
                if (it.type == "lineTicket") {
                    lineTickets.add(it)
                }
            }
            val list : String = GsonBuilder().create().toJson(lineTickets)
            startActivity<StoreCategoryActivity>(
                "title" to "Vonaljegyek",
                "list" to list
            )
        }

        time_ticket_btn.setOnClickListener {
            val timeTickets = ArrayList<TicketTypeWithPrice>()
            tickets.forEach {
                if (it.type == "timeTicket") {
                    timeTickets.add(it)
                }
            }
            val list : String = GsonBuilder().create().toJson(timeTickets)
            startActivity<StoreCategoryActivity>(
                "title" to "Időalapú jegyek",
                "list" to list
            )
        }
    }
}
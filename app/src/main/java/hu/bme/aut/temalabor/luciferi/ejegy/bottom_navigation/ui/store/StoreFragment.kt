package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.TicketTypeWithPrice
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store.adapter.StoreRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_store.*
import org.jetbrains.anko.support.v4.startActivity

class StoreFragment : Fragment() {

    //private lateinit var storeViewModel: StoreViewModel
    private var tickets = ArrayList<TicketTypeWithPrice>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerview_tickets.layoutManager =LinearLayoutManager(context).apply { orientation = RecyclerView.HORIZONTAL }
        recyclerview_tickets.setHasFixedSize(true)

        val adapter = StoreRecyclerAdapter(context!!)
        recyclerview_tickets.adapter = adapter

        MyAsyncGetTicketTypesWithPrice {list->

            tickets=ArrayList(list!!)
            tickets.forEach { adapter.addTicket(it) }
            adapter.notifyDataSetChanged()

            adapter.setOnItemCickListener(object : StoreRecyclerAdapter.OnItemClickListener{
                override fun onItemClick(position : Int) {
                    val ticket : String = GsonBuilder().create().toJson(tickets[position])
                    startActivity<BuyTicketActivity>(
                        "ticket" to ticket
                    )
                }
            })
        }.execute()

        pass_ticket_btn.setOnClickListener {
            passTickets(it)
        }
        line_ticket_btn.setOnClickListener {
            lineTickets(it)
        }
        time_ticket_btn.setOnClickListener {
            timeTickets(it)
        }
    }

    fun passTickets(v: View) {
        val passTickets = ArrayList<TicketTypeWithPrice>()
        tickets.forEach {
            if (it.type == "passTicket") {
                passTickets.add(it)
            }
        }
        val list : String = GsonBuilder().create().toJson(passTickets)
        startActivity<StoreCategoryActivity>(
            "title" to getString(R.string.season_tickets),
            "list" to list
        )
    }
    fun lineTickets(v: View) {
        val lineTickets = ArrayList<TicketTypeWithPrice>()
        tickets.forEach {
            if (it.type == "lineTicket") {
                lineTickets.add(it)
            }
        }
        val list : String = GsonBuilder().create().toJson(lineTickets)
        startActivity<StoreCategoryActivity>(
            "title" to getString(R.string.line_tickets),
            "list" to list
        )
    }
    fun timeTickets(v: View) {
        val timeTickets = ArrayList<TicketTypeWithPrice>()
        tickets.forEach {
            if (it.type == "timeTicket") {
                timeTickets.add(it)
            }
        }
        val list : String = GsonBuilder().create().toJson(timeTickets)
        startActivity<StoreCategoryActivity>(
            "title" to getString(R.string.timebased_tickets),
            "list" to list
        )
    }

    class MyAsyncGetTicketTypesWithPrice(val callback : (List<TicketTypeWithPrice>?) -> Unit) : AsyncTask<String, Unit, List<TicketTypeWithPrice>?>(){
        override fun doInBackground(vararg params: String?): List<TicketTypeWithPrice>? {
            val callSyncGetTickets = RetrofitClient.api.getTickets()
            val response = callSyncGetTickets.execute()
            return response.body()
        }

        override fun onPostExecute(result: List<TicketTypeWithPrice>?) {
            super.onPostExecute(result)
            callback.invoke(result)
        }
    }
}
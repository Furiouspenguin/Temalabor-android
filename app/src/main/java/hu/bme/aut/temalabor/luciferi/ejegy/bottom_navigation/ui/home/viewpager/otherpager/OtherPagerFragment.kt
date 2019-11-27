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
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.fragment_pager_other.*
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity

class OtherPagerFragment : Fragment(){

    private var tickets = ArrayList<UserTicket>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager_other,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        invalid_recyclerview.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        val adapter = UserTicketsRecycleAdapter(context!!)
        invalid_recyclerview.adapter = adapter

        RestApiRepository.liveInvalidUserTickets.observe(this, Observer { usertickets->
            tickets=ArrayList(usertickets)
            tickets.forEach {
                adapter.addTicket(it)
            }
            adapter.notifyDataSetChanged()
        })

        adapter.setOnItemCickListener(object : UserTicketsRecycleAdapter.OnItemClickListener{
            override fun onItemClick(ticketPosition: Int) {
                startActivity<UserTicketActivity>(
                    "position" to ticketPosition,
                    "source" to "invalid"
                )
            }
        })
    }
}
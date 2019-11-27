package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.idpager

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.InspectTicket
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager.UserTicketActivity
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager.adapter.UserTicketsRecycleAdapter
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.activity_inspect.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class InspectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspect)

        RestApiRepository.inspectedTickets = null

        try {
            MyAsyncInspect(intent.getStringExtra("id")){
                if (it == null) {
                    toast("ERROR")
                    finish()
                }
                else {
                    title = getString(R.string.user)
                    inspect_id.text = it.user.id
                    inspect_name.text = it.user.name
                    inspect_type.text = it.user.type
                    if (it.status == "validated") {
                        inspect_status.text = getString(R.string.valid)
                        inspect_rl_status.background = ContextCompat.getDrawable(this,R.color.green_OK)
                    }
                    else {
                        inspect_status.text = getString(R.string.invalid)
                        inspect_rl_status.background = ContextCompat.getDrawable(this,R.color.red_BAD)
                    }

                    inspect_tickets.layoutManager = LinearLayoutManager(this,
                        RecyclerView.VERTICAL,false)
                    val adapter = UserTicketsRecycleAdapter(this)
                    inspect_tickets.adapter = adapter
                    it.tickets.forEach { ticket ->
                        adapter.addTicket(ticket)
                    }
                    adapter.notifyDataSetChanged()

                    RestApiRepository.inspectedTickets = it.tickets

                    adapter.setOnItemCickListener(object : UserTicketsRecycleAdapter.OnItemClickListener{
                        override fun onItemClick(ticketPosition: Int) {
                            startActivity<UserTicketActivity>(
                                "position" to ticketPosition,
                                "source" to "inspected"
                            )
                        }
                    })
                }
            }.execute()
        }
        catch (e : Exception){
            e.printStackTrace()
        }
    }

    class MyAsyncInspect(val ticketId: String, val callback: (InspectTicket?) -> Unit) :
        AsyncTask<String, Unit, InspectTicket?>() {
        override fun doInBackground(vararg params: String?): InspectTicket? {
            if (RestApiRepository.inspectorVehicle == null) {
                return null
            } else {
                val callSyncGetUserTickets = RetrofitClient.api.postTicketsInspect(
                    ticketId = ticketId,
                    vehicleId = RestApiRepository.inspectorVehicle!!
                )

                val response = callSyncGetUserTickets.execute()
                return response.body()
            }
        }

        override fun onPostExecute(result: InspectTicket?) {
            super.onPostExecute(result)
            callback.invoke(result)
        }
    }
}

package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import org.jetbrains.anko.longToast
import java.lang.Exception

class UserTicketActivity : AppCompatActivity() {

    private lateinit var ticket : UserTicket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_ticket)

        try {
            ticket = RestApiRepository.getUserTickets(RestApiRepository.getUserData().value!!.id).value!![intent.getIntExtra("position",-1)]
        } catch (e : Exception) {
            e.printStackTrace()
        }
        longToast(ticket.validUntil)
    }
}

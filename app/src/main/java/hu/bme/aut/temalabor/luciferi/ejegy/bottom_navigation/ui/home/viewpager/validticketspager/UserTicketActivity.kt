package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.activity_user_ticket.*
import net.glxn.qrgen.android.QRCode
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
        //longToast(ticket.validUntil)
        title = ticket.ticketType.name
        ticket_id_number.text = ticket.ticketType.typeId
        ticket_valid_from.text = ticket.validFrom.subSequence(0,10)
        ticket_valid_until.text = ticket.validUntil.subSequence(0,10)

        val myBitmap = QRCode.from(ticket.id).withSize(500,500).bitmap()
        ticket_qr.setImageBitmap(myBitmap)
    }
}

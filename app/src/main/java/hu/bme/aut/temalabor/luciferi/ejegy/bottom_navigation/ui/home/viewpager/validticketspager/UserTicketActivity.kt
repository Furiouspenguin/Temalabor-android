package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.activity_user_passticket.*
import net.glxn.qrgen.android.QRCode
import java.lang.Exception

class UserTicketActivity : AppCompatActivity() {

    private lateinit var ticket : UserTicket
    private var valid : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        try {
            valid = intent.getBooleanExtra("valid",false)
            if (valid) {
                ticket = RestApiRepository.getValidUserTickets().value!![intent.getIntExtra("position",-1)]

            } else {
                ticket = RestApiRepository.getInvalidUserTickets().value!![intent.getIntExtra("position",-1)]
            }

        } catch (e : Exception) {
            e.printStackTrace()
        }

        title = ticket.ticketType.name
        var color : Int = 0x11ABDB.toInt()

        when(ticket.ticketType.type) {
            "passTicket" -> {
                setContentView(R.layout.activity_user_passticket)
                ticket_id_number.text = ticket.ticketType.typeId
                if (valid) {
                    ticket_valid_from.text = ticket.validFrom.subSequence(0,10)
                    ticket_valid_until.text = ticket.validUntil.subSequence(0,10)
                }
                else {
                    if (ticket.validFrom.isNullOrEmpty()) {
                        ticket_valid_from.text = "-"
                    }
                    else {
                        ticket_valid_from.text = ticket.validFrom.subSequence(0,10)
                    }
                    if (ticket.validUntil.isNullOrEmpty()) {
                        ticket_valid_until.text = "-"
                    }
                    else {
                        ticket_valid_until.text = ticket.validUntil.subSequence(0,10)
                    }
                }
                color = 0xF1C40F.toInt()
            }
            "lineTicket" -> {
                setContentView(R.layout.activity_user_lineticket)
                if (valid) {
                    ticket_valid_until.text = ticket.validUntil.subSequence(11,16)
                }
                else {
                    if (ticket.validUntil.isNullOrEmpty()) {
                        ticket_valid_until.text = "-"
                    }
                    else {
                        ticket_valid_until.text = ticket.validUntil.subSequence(11,16)
                    }
                }
                color = 0xE612B4AA.toInt()
            }
            "timeTicket" -> {
                setContentView(R.layout.activity_user_timeticket)
                if (valid) {
                    ticket_valid_until.text = "${ticket.validUntil.subSequence(0,10)}. : ${ticket.validUntil.subSequence(11,16)}"
                }
                else {
                    if (ticket.validUntil.isNullOrEmpty()) {
                        ticket_valid_until.text = "-"
                    }
                    else {
                        ticket_valid_until.text = "${ticket.validUntil.subSequence(0,10)}. : ${ticket.validUntil.subSequence(11,16)}"
                    }
                }
                color = 0x11ABDB.toInt()
            }
        }

        val myBitmap = QRCode.from(ticket.id).withColor(0xFFFFFFFF.toInt(),color).withSize(500,500).bitmap()
        ticket_qr.setImageBitmap(myBitmap)
    }
}

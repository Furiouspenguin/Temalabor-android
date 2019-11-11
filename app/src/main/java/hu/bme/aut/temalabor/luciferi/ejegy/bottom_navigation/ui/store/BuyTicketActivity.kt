package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.TicketTypeWithPrice
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.activity_buy_passticket.*
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class BuyTicketActivity : AppCompatActivity(), DatePickerDialogFragment.DateListener {

    private var ticket : TicketTypeWithPrice? = null

    private var type : String = "lineTicket"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        try {
            val type = object : TypeToken<TicketTypeWithPrice>() {}.type
            ticket = Gson().fromJson(intent.getStringExtra("ticket"),type)
        } catch (e : Exception) {
            e.printStackTrace()
        }

        if (ticket == null) {
            setContentView(R.layout.activity_buy_lineticket)
            title = "Error: couldn't find data!"
        } else {
            when(ticket?.type) {
                "passTicket" -> {
                    setContentView(R.layout.activity_buy_passticket)
                    type = "passTicket"

                    val date = Calendar.getInstance()
                    val df = SimpleDateFormat("yyyy-MM-dd")
                    val dateString = df.format(date.time)
                    buy_valid_from_date.text = dateString
                    when(ticket?.validTimeUnit) {
                        "day" -> {
                            date.add(Calendar.DATE,ticket?.validFor!!)
                            buy_valid_until_date.text = df.format(date.time)
                        }
                        "hour" -> {
                            date.add(Calendar.HOUR,ticket?.validFor!!)
                            buy_valid_until_date.text = df.format(date.time)
                        }
                    }

                    buy_id_number.text = RestApiRepository.getUserData().value?.idCard
                }
                "lineTicket" -> setContentView(R.layout.activity_buy_lineticket)
                "timeTicket" -> {
                    setContentView(R.layout.activity_buy_timeticket)
                    type = "timeTicket"


                    val date = Calendar.getInstance()
                    val df = SimpleDateFormat("MM-dd hh:mm")
                    val dateString = df.format(date.time)
                    buy_valid_from_date.text = dateString
                    when(ticket?.validTimeUnit) {
                        "day" -> {
                            date.add(Calendar.DATE,ticket?.validFor!!)
                            buy_valid_until_date.text = df.format(date.time)
                        }
                        "hour" -> {
                            date.add(Calendar.HOUR,ticket?.validFor!!)
                            buy_valid_until_date.text = df.format(date.time)
                        }
                    }
                }
            }
            title = ticket?.name
        }
    }

    fun setValidFrom(v : View) {
        val datePicker = DatePickerDialogFragment((type == "passTicket"))
        datePicker.show(supportFragmentManager,DatePickerDialogFragment.TAG)
    }

    fun setValidUntil(v : View) {
        toast("Function not yet implemented")
    }

    override fun onPassDateSelected(day: Int, month: Int, year: Int) {
        val date = Calendar.getInstance()
        date.set(year,month,day)
        val df = SimpleDateFormat("yyyy-MM-dd")

        val dateString = df.format(date.time)
        buy_valid_from_date.text = dateString
        when(ticket?.validTimeUnit) {
            "day" -> {
                date.add(Calendar.DATE,ticket?.validFor!!)
                buy_valid_until_date.text = df.format(date.time)
            }
            "hour" -> {
                date.add(Calendar.HOUR,ticket?.validFor!!)
                buy_valid_until_date.text = df.format(date.time)
            }
        }
    }

    override fun onTimeDateSelected(hour: Int, minute: Int) {
        val date = Calendar.getInstance()
        date.set(date.get(Calendar.YEAR),date.get(Calendar.MONTH),date.get(Calendar.DATE),hour, minute)
        val df = SimpleDateFormat("MM-dd HH:mm")
        val dateString = df.format(date.time)
        buy_valid_from_date.text = dateString
        when(ticket?.validTimeUnit) {
            "day" -> {
                date.add(Calendar.DATE,ticket?.validFor!!)
                buy_valid_until_date.text = df.format(date.time)
            }
            "hour" -> {
                date.add(Calendar.HOUR,ticket?.validFor!!)
                buy_valid_until_date.text = df.format(date.time)
            }
        }
    }
}

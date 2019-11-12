package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.afterTextChanged
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.TicketTypeWithPrice
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.activity_buy_passticket.*
import okhttp3.Response
import okhttp3.ResponseBody
import org.jetbrains.anko.toast
import retrofit2.Call
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

                    buy_pay_btn.setOnClickListener {
                        MyAsyncBuy(buy_valid_from_date.text.toString(),ticket!!.typeId,edit_quantity.text.toString().toInt())
                        finish()
                        RestApiRepository.setUserTicketsFromApi()
                    }

                    buy_id_number.text = RestApiRepository.getUserData().value?.idCard
                }
                "lineTicket" -> {
                    setContentView(R.layout.activity_buy_lineticket)
                    buy_pay_btn.setOnClickListener {
                        MyAsyncBuy(null,ticket!!.typeId,edit_quantity.text.toString().toInt())
                        finish()
                        RestApiRepository.setUserTicketsFromApi()
                    }
                }
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
                    buy_pay_btn.setOnClickListener {
                        MyAsyncBuy(buy_valid_from_date.text.toString(),ticket!!.typeId,edit_quantity.text.toString().toInt())
                        finish()
                        RestApiRepository.setUserTicketsFromApi()
                    }
                }
            }
            title = ticket?.name
            edit_quantity.setText(1.toString())
            buy_pay_number.text = ticket?.price.toString()
            edit_quantity.afterTextChanged {
                buy_pay_number.text = (it.toInt() * ticket?.price!!).toString()
            }
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


    class MyAsyncBuy(val fromDate : String? = null, val typeId: String, val count : Int) : AsyncTask<String,Unit,ResponseBody>(){
        override fun doInBackground(vararg params: String?): ResponseBody {
            var callSyncBuy : Call<ResponseBody>
            if (fromDate != null) callSyncBuy = RetrofitClient.api.postTicketsBuyPassTicket(typeId,fromDate,count)
            else callSyncBuy = RetrofitClient.api.postTicketsBuy(typeId,count)

            val response =callSyncBuy.execute()
            return response.body()!!
        }

    }
}

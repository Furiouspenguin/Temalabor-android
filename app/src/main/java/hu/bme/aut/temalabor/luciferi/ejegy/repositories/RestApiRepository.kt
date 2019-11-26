package hu.bme.aut.temalabor.luciferi.ejegy.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserData
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object RestApiRepository {

    private var userData : UserData? = null
    fun getUserData() : MutableLiveData<UserData> = MutableLiveData<UserData>().apply { value = userData }
    fun setUserData(userData: UserData){
        this.userData = userData

        RetrofitClient.MyAsyncGetUserTickets(userData.id){
            setUserTickets(it)
        }.execute()



        setLiveUserTicketsFromBackend()
    }


    //üres listákat adunk az elején
    var liveValidUserTickets : LiveData<List<UserTicket>> = MutableLiveData<List<UserTicket>>().apply {
        value = listOf()
    }
    var liveInvalidUserTickets : LiveData<List<UserTicket>> = MutableLiveData<List<UserTicket>>().apply {
        value = listOf()
    }
    fun setLiveUserTicketsFromBackend(){
        RetrofitClient.MyAsyncGetUserTickets(userData!!.id){
            val invalidUserTickets = ArrayList<UserTicket>()
            val validUserTickets = ArrayList<UserTicket>()
            it.forEach { ticket->
                val now = Calendar.getInstance().time
                if (ticket.validFrom.isNullOrEmpty() or ticket.validUntil.isNullOrEmpty()) {
                    invalidUserTickets.add(ticket)
                } else {
                    val df = SimpleDateFormat("yyyy-MM-dd",Locale.GERMAN)
                    val validFromDate = df.parse(ticket.validFrom.subSequence(0,10).toString())

                    val isDue =  (0 > validFromDate.compareTo(now))
                    if (isDue) {
                        validUserTickets.add(ticket)
                    }
                    else {
                        invalidUserTickets.add(ticket)
                    }
                }
            }
            liveInvalidUserTickets = MutableLiveData<List<UserTicket>>().apply {
                value = invalidUserTickets
            }
            liveValidUserTickets = MutableLiveData<List<UserTicket>>().apply {
                value = validUserTickets
            }
        }.execute()
    }




    private var validUserTickets = ArrayList<UserTicket>()
    private var invalidUserTickets = ArrayList<UserTicket>()

    fun getValidUserTickets() : MutableLiveData<List<UserTicket>> =
        MutableLiveData<List<UserTicket>>().apply { value = validUserTickets }

    fun getInvalidUserTickets() : MutableLiveData<List<UserTicket>> =
        MutableLiveData<List<UserTicket>>().apply { value = invalidUserTickets }

    fun setUserTickets(tickets : List<UserTicket>){
        tickets.forEach {
            val now = Calendar.getInstance().time
            if (it.validFrom.isNullOrEmpty() or it.validUntil.isNullOrEmpty()) {
                invalidUserTickets.add(it)
            } else {
                val df = SimpleDateFormat("yyyy-MM-dd",Locale.GERMAN)
                val validFromDate = df.parse(it.validFrom.subSequence(0,10).toString())

                val isDue =  (0 > validFromDate.compareTo(now))
                if (isDue) {
                    validUserTickets.add(it)
                }
                else {
                    invalidUserTickets.add(it)
                }
            }
        }
    }

    fun setUserTicketsFromApi(){
        RetrofitClient.MyAsyncGetUserTickets(userData!!.id){
            setUserTickets(it)
        }.execute()
    }

    var inspectorVehicle : String? = null

}
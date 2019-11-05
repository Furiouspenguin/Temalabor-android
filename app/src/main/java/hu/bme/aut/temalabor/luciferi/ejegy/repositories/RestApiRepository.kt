package hu.bme.aut.temalabor.luciferi.ejegy.repositories

import androidx.lifecycle.MutableLiveData
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserData
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient

object RestApiRepository {

    private var userData : UserData? = null

    fun getUserData() : MutableLiveData<UserData> = MutableLiveData<UserData>().apply { value = userData }

    fun setUserData(userData: UserData){
        this.userData = userData
    }


    private var userTickets = ArrayList<UserTicket>()

    fun getUserTickets(userId : String) : MutableLiveData<List<UserTicket>> = MutableLiveData<List<UserTicket>>().apply {
        value = userTickets
    }

    fun setUserTickets(tickets : List<UserTicket>){
        userTickets = ArrayList(tickets)
    }





}
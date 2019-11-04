package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository

class ValidTicketsPagerViewModel : ViewModel(){

    private var _tickets : MutableLiveData<List<UserTicket>>? = null
    init {
        if (_tickets == null) {
            _tickets = RestApiRepository.getUserTickets(RestApiRepository.getUserData().value!!.id)
        }
    }
    var tickets : LiveData<List<UserTicket>>? = _tickets
}
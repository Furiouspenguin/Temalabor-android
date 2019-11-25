package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.validticketspager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository

class ValidTicketsPagerViewModel : ViewModel(){
    private var _tickets : MutableLiveData<List<UserTicket>>? = null
    init {
        if ((_tickets == null)) {//or (_tickets?.value.isNullOrEmpty())) {
            //_tickets = RestApiRepository.getUserTickets()
            _tickets = RestApiRepository.getValidUserTickets()
            /*if ((_tickets == null) or  _tickets?.value.isNullOrEmpty()) {
                RetrofitClient.MyAsyncGetUserTickets(RestApiRepository.getUserData().value!!.id){
                    //_tickets = MutableLiveData(it)

                    RestApiRepository.setUserTickets(it)
                    _tickets = MutableLiveData<List<UserTicket>>()
                    _tickets = RestApiRepository.getValidUserTickets()
                    tickets = RestApiRepository.getValidUserTickets()
                }.execute()
            }*/
        }
    }
    var tickets : LiveData<List<UserTicket>>? = _tickets
}
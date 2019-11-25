package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.otherpager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserTicket
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository

class OtherPagerViewModel : ViewModel() {
    private var _tickets : MutableLiveData<List<UserTicket>>? = null
    init {
        if (_tickets == null) {
            //_tickets = RestApiRepository.getUserTickets()
            _tickets = RestApiRepository.getInvalidUserTickets()
/*
            if (_tickets == null || _tickets?.value.isNullOrEmpty()) {
                RetrofitClient.MyAsyncGetUserTickets(RestApiRepository.getUserData().value!!.id){
                    _tickets = MutableLiveData(it)

                    RestApiRepository.setUserTickets(it)
                }.execute()
            }
*/
        }
    }
    var tickets : LiveData<List<UserTicket>>? = _tickets
}
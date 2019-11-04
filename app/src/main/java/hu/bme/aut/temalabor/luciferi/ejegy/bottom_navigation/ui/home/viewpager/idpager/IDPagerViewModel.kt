package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.idpager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserData
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository

class IDPagerViewModel : ViewModel(){
    private var _userData : MutableLiveData<UserData>? = null
    init {
        if (_userData == null) {
            _userData = RestApiRepository.getUserData()
        }
    }
    var userData : LiveData<UserData>? = _userData
}
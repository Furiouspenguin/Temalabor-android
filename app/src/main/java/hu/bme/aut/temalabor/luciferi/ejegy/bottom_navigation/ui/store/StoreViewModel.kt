package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.store

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.TicketTypeWithPrice
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient.api

class StoreViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Kedvencek"
    }
    val text: LiveData<String> = _text


    ///////////////////////
    /////////Saját/////////
    ///////////////////////



    private  var _tickets = MutableLiveData<List<TicketTypeWithPrice>>()
    var tickets : LiveData<List<TicketTypeWithPrice>> = _tickets

    init {
        MyAsyncGetTicketTypesWithPrice{
            _tickets = MutableLiveData<List<TicketTypeWithPrice>>().apply {
                if (it != null) value = it
            }
            tickets = _tickets
        }.execute()
    }

    class MyAsyncGetTicketTypesWithPrice(val callback : (List<TicketTypeWithPrice>?) -> Unit) : AsyncTask<String,Unit,List<TicketTypeWithPrice>?>(){
        override fun doInBackground(vararg params: String?): List<TicketTypeWithPrice>? {
            val callSyncGetTickets = api.getTickets()
            val response = callSyncGetTickets.execute()
            return response.body()
        }

        override fun onPostExecute(result: List<TicketTypeWithPrice>?) {
            super.onPostExecute(result)
            callback.invoke(result)
        }
    }
}
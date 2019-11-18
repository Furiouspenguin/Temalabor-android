package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.idpager

import android.app.Activity
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.zxing.Result
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import me.dm7.barcodescanner.zxing.ZXingScannerView
import okhttp3.ResponseBody
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class QRScannerActivity : Activity(), ZXingScannerView.ResultHandler {
    private lateinit var mScannerView : ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)
    }

    override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        longToast(rawResult.toString())

        MyAsyncValidate(rawResult.toString()){
            toast(it)
            RestApiRepository.setUserTicketsFromApi()
        }.execute()
        finish()
    }

    class MyAsyncValidate(val vehicleId : String, val callback : (String) -> Unit) : AsyncTask<String,Unit,String>(){
        override fun doInBackground(vararg params: String?): String {
            val callSyncGetUserTickets = RetrofitClient.api.postTicketsValidate(vehicleId)

            val response =callSyncGetUserTickets.execute()
            return response.message()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            callback.invoke(result ?: "nothing")
        }
    }
}

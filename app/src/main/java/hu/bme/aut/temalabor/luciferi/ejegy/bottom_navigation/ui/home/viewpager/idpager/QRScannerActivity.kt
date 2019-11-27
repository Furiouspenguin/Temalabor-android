package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.ui.home.viewpager.idpager

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import com.google.zxing.Result
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.lang.Exception

class QRScannerActivity : Activity(), ZXingScannerView.ResultHandler {
    private lateinit var mScannerView: ZXingScannerView
    private lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScannerView = ZXingScannerView(this)
        setContentView(mScannerView)

        try {
            type = intent.getStringExtra("type")

        } catch (e: Exception) {
            e.printStackTrace()
        }
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

        when (type) {
            "validate" -> {
                MyAsyncValidate(rawResult.toString()) {
                    toast(it)
                    RestApiRepository.setUserTicketsFromBackend()
                }.execute()
            }
            "inspect" -> {
                MyAsyncInspect(rawResult.toString()) {
                    toast(it)
                }.execute()
            }
            "vehicle" -> {
                RestApiRepository.inspectorVehicle = rawResult.toString()
            }
        }
        finish()
    }

    class MyAsyncValidate(val vehicleId: String, val callback: (String) -> Unit) :
        AsyncTask<String, Unit, String>() {
        override fun doInBackground(vararg params: String?): String {
            val callSyncGetUserTickets = RetrofitClient.api.postTicketsValidate(vehicleId)

            val response = callSyncGetUserTickets.execute()
            return response.message()
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            callback.invoke(result ?: "nothing")
        }
    }

    class MyAsyncInspect(val ticketId: String, val callback: (String) -> Unit) :
        AsyncTask<String, Unit, String>() {
        override fun doInBackground(vararg params: String?): String {
            if (RestApiRepository.inspectorVehicle == null) {
                return "Set vehicle first!"
            } else {
                val callSyncGetUserTickets = RetrofitClient.api.postTicketsInspect(
                    ticketId = ticketId,
                    vehicleId = RestApiRepository.inspectorVehicle!!
                )

                val response = callSyncGetUserTickets.execute()
                return response.body()?.status ?: response.message()
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            callback.invoke(result ?: "nothing")
        }
    }
}

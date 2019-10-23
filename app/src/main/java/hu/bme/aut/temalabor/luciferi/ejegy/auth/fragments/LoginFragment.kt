package hu.bme.aut.temalabor.luciferi.ejegy.auth.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.afterTextChanged
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.User
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitApi
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient.api
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit

class LoginFragment :Fragment(){
    private var btnEnableUser = false
    private var btnEnablePwd = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username.afterTextChanged { text ->
            if (text.isEmpty()){
                username.error = "Need email"
                username.requestFocus()
                btnEnableUser = false
                login.isEnabled = false
            } else btnEnableUser = true
            if (btnEnableUser and btnEnablePwd) login.isEnabled = true
        }
        password.afterTextChanged { text ->
            if (text.isEmpty()){
                password.error = "Need password"
                password.requestFocus()
                btnEnablePwd = false
                login.isEnabled = false
            } else btnEnablePwd = true
            if (btnEnableUser and btnEnablePwd) login.isEnabled = true
        }

        login.setOnClickListener {
            val usernameString = username.text.toString().trim()
            val passwordString = password.text.toString().trim()
            //login backend elérése
            if (usernameString.isNotEmpty() and passwordString.isNotEmpty()){
                //do something
                MyAsyncLogin(usernameString,passwordString){user ->
                    Toast.makeText(context,"Curent user data:\n${user.toString()}",Toast.LENGTH_LONG).show()
                }.execute()
            }
        }
    }

    private class MyAsyncLogin(val username : String,val  password : String, val callback : (User?) -> Unit) : AsyncTask<String, Unit, User?>(){
        override fun doInBackground(vararg p0: String?): User? {
            val callSyncLoginUser = api.loginUser(username,password)

            val response =callSyncLoginUser.execute()
            return response.body()
        }

        override fun onPostExecute(result: User?) {
            super.onPostExecute(result)
            callback.invoke(result)
        }
    }
}
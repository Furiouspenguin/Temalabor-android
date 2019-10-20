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
import kotlinx.android.synthetic.main.fragment_login.*
import okhttp3.*
import okhttp3.internal.cacheGet
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
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
                MyAsyncLogIn(usernameString,passwordString){result ->
                    /*if (result.toInt() != 302){
                        throw Exception("Unauthorized")
                    }*/
                    Toast.makeText(context,result,Toast.LENGTH_LONG).show()
                }.execute()


                /*MyAsyncGetUserData{result ->
                    Toast.makeText(context,result,Toast.LENGTH_LONG).show()
                }.execute()*/
            }
        }
    }


}

private class MyAsyncLogIn(val username : String,val  password : String, val callback : (String) -> Unit) : AsyncTask<String, Unit, String>(){
    override fun doInBackground(vararg p0: String): String {

        var reader : BufferedReader? = null
        var result : String = ""
        try {
            val url = URL("https://temalabor2019.azurewebsites.net/api/auth/login")
            val conn = url.openConnection() as HttpURLConnection
            conn.apply {
                requestMethod = "POST"

                //addRequestProperty("Content-Type","application/json; utf-8")
                //addRequestProperty("Accept","application/json")
                doOutput = true
                doInput = true

                useCaches = false
                outputStream.write("email=$username&password=$password".toByteArray())
                outputStream.flush()



                reader = BufferedReader(InputStreamReader(inputStream))
            }

            var line : String?
            do {
                line = reader?.readLine()
                result += line
            } while (line != null)

            //val something = conn.headerFields
            //return conn.responseCode.toString()
            return result
        } catch (e : IOException){
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader!!.close()
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }
        }





/*
        var result1 : String = ""

        val client = OkHttpClient.Builder()
            .connectTimeout(5000,TimeUnit.MILLISECONDS)
            .authenticator(object : Authenticator{
                override fun authenticate(route: Route?, response: Response): Request? {
                    result1 = response.headers.toString()
                    val credential = Credentials.basic(username,password)
                    if (credential.equals(response.request.header("Authorization")))
                        return null
                    return response.request.newBuilder()
                        .header("Authorization",credential)
                        .build()
                }
            })
            .build()
        val request = Request.Builder()
            .url("https://temalabor2019.azurewebsites.net/api/auth/login")
            .build()
        var response : Response? = null
        try {
            response = client.newCall(request).execute()
            val requestPrev = response.request
            return "Response1:\n" + result1 + "\nRequest2:\n" + requestPrev.headers.toString() + "\nResponse2:\n" + response.body.toString()
        } catch (e : IOException){
            e.printStackTrace()
        }*/

        return "ERROR"
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        callback.invoke(result)
    }
}

private class MyAsyncGetUserData(val callback : (String) -> Unit) : AsyncTask<String, Unit, String>(){
    override fun doInBackground(vararg p0: String): String {

        val client=OkHttpClient.Builder()
            .connectTimeout(5000,TimeUnit.MILLISECONDS)
            .build()
        val request=Request.Builder()
            .url(
                "https://temalabor2019.azurewebsites.net/api/user")
            .get()
            .build()
        val call=client.newCall(request)
        val response=call.execute()
        val responseStr=response.body!!.string()
        return responseStr
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        callback.invoke(result)
    }
}
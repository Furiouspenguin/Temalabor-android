package hu.bme.aut.temalabor.luciferi.ejegy.auth.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.afterTextChanged
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserData
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient.api
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.doAsyncResult
import org.jetbrains.anko.support.v4.longToast

class LoginFragment :Fragment(){
    private var btnEnableUser = false
    private var btnEnablePwd = false

    private var listener: OnFragmentInteractionListener? = null
    private var userData : UserData? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username.afterTextChanged { text ->
            if (text.isEmpty()){
                username.error = "Need email!"
                username.requestFocus()
                btnEnableUser = false
                login.isEnabled = false
            } else btnEnableUser = true
            if (btnEnableUser and btnEnablePwd) login.isEnabled = true
        }
        password.afterTextChanged { text ->
            if (text.isEmpty()){
                password.error = "Need password!"
                password.requestFocus()
                btnEnablePwd = false
                login.isEnabled = false
            } else btnEnablePwd = true
            if (btnEnableUser and btnEnablePwd) login.isEnabled = true
        }

        sign_up_text.setOnClickListener {
            listener?.registerNow()
        }

        login.setOnClickListener {
            val usernameString = username.text.toString().trim()
            val passwordString = password.text.toString().trim()
            //login backend elérése
            if (usernameString.isNotEmpty() and passwordString.isNotEmpty()){
                //do something
                RetrofitClient.MyAsyncLogin(usernameString, passwordString) { user ->
                    //longToast("Curent user data:\n${user.toString()}")
                    userData = user
                    if (user != null){
                        listener?.loginSuccess(user)
                    } else {
                        longToast("Coulnd't fetch data!")
                    }
                }.execute()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener{
        fun registerNow()
        fun loginSuccess(userData : UserData)
    }
}
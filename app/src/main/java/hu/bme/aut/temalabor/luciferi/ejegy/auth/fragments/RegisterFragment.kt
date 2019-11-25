package hu.bme.aut.temalabor.luciferi.ejegy.auth.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.afterTextChanged
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserData
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient.api
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.email
import org.jetbrains.anko.support.v4.longToast

class RegisterFragment : Fragment(){

    private var userData : UserData? = null

    private var btnEnableUser = false
    private var btnEnableEmail = false
    private var btnEnableIdCard = false
    private var btnEnablePwd = false
    private var btnEnablePwdConfirm = false

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sign_in_text.setOnClickListener {
            listener?.loginNow()
        }

        name.afterTextChanged{text ->
            if (text.isEmpty()){
                name.error = "Need name!"
                name.requestFocus()
                btnEnableUser = false
                register.isEnabled = false
            } else btnEnableUser = true
            enableRegBtn()
        }
        email.afterTextChanged{ text ->
            if (text.isEmpty()){
                email.error = "Need email!"
                email.requestFocus()
                btnEnableEmail = false
                register.isEnabled = false
            } else btnEnableEmail = true
            enableRegBtn()
        }
        idNumber.afterTextChanged {text ->
            if (text.isEmpty()){
                idNumber.error = "Need ID number!"
                idNumber.requestFocus()
                btnEnableIdCard = false
                register.isEnabled = false
            } else btnEnableIdCard = true
            enableRegBtn()
        }
        password.afterTextChanged { text ->
            if (text.isEmpty()) {
                password.error = "Need password!"
                password.requestFocus()
                btnEnablePwd = false
                register.isEnabled = false
            } else btnEnablePwd = true
            enableRegBtn()
        }
        password_confirm.afterTextChanged { text ->
            if (text.isEmpty()) {
                password_confirm.error = "Need password!"
                password_confirm.requestFocus()
                btnEnablePwdConfirm = false
                register.isEnabled = false
            }
            if (text != password.text.toString()) {
                password_confirm.error = "Password's not matching!"
                password_confirm.requestFocus()
            }
            else btnEnablePwdConfirm = true
            enableRegBtn()
        }

        register.setOnClickListener {
            val nameString = name.text.toString()
            val emailString = email.text.toString().trim()
            val passwordString = password.text.toString().trim()
            val idNumberString = idNumber.text.toString().trim()
            if (nameString.isNotEmpty() and emailString.isNotEmpty() and passwordString.isNotEmpty() and idNumberString.isNotEmpty()) {

                loading.visibility = View.VISIBLE

                MyAsyncRegister(nameString,emailString,passwordString,idNumberString){ user->
                    userData = user
                    if (user != null){
                        //adatok elmentése
                        RestApiRepository.setUserData(user)
                        //activity értesítése a sikeres regisztrációról
                        listener?.registerSuccess(user)
                    } else {
                        longToast("Coulnd't fetch data!")
                    }

                    loading.visibility = View.GONE
                }.execute()
            }
        }
    }

    fun enableRegBtn(){
        if (btnEnableUser and btnEnableEmail and btnEnableIdCard and btnEnablePwd and btnEnablePwdConfirm) register.isEnabled = true
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

    private class MyAsyncRegister(
        val name : String,
        val email : String,
        val password : String,
        val idNumber : String,
        val callback : (UserData?) -> Unit
    ) : AsyncTask<String,Unit, UserData?>(){
        override fun doInBackground(vararg params: String?): UserData? {
            val callSyncRegister = api.register(name = name, email = email, password = password, idCard = idNumber)
            val response =callSyncRegister.execute()
            return response.body()
        }
        override fun onPostExecute(result:UserData?) {
            super.onPostExecute(result)
            callback.invoke(result)
        }
    }


    interface OnFragmentInteractionListener{
        fun loginNow()//sima backpressed elég ehhez, mivel úgyis hamarabb volt a login mint a register
        fun registerSuccess(userData : UserData)
    }
}
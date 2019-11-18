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
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.service.RetrofitClient
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import hu.bme.aut.temalabor.luciferi.ejegy.room.AppDatabase
import hu.bme.aut.temalabor.luciferi.ejegy.room.User
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast

class LoginFragment :Fragment(){
    private var btnEnableUser = false
    private var btnEnablePwd = false

    private var listener: OnFragmentInteractionListener? = null
    private var userData : UserData? = null

    private lateinit var db : AppDatabase
    private var emptyUserList : Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase(context!!)

        AsyncGetDataFromRoom(db) { user ->
            if (!user.email.isNullOrEmpty() and !user.password.isNullOrEmpty()) {
                emptyUserList = false
                email.setText(user.email)
                password.setText(user.password)

                //loading.visibility = View.VISIBLE
            }
        }.execute()


        email.afterTextChanged { text ->
            if (text.isEmpty()){
                email.error = "Need email!"
                email.requestFocus()
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
            val usernameString = email.text.toString().trim()
            val passwordString = password.text.toString().trim()
            //login backend elérése
            if (usernameString.isNotEmpty() and passwordString.isNotEmpty()){
                //do something
                RetrofitClient.MyAsyncLogin(usernameString, passwordString) { user ->
                    //longToast("Curent user data:\n${user.toString()}")
                    userData = user
                    if (user != null){
                        //bejelentkezési adatok elmentése
                        Thread(){
                            db.userDao().add(User(email = usernameString, password = passwordString))
                            toast("data set")
                        }.start()

                        //adatok elmentése
                        RestApiRepository.setUserData(user)
                        //activity értesítése a sikeres bejelentkezésről
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

    class AsyncGetDataFromRoom(val db : AppDatabase, val callback : (User) -> Unit) : AsyncTask<User, Unit, User>() {
        override fun doInBackground(vararg params: User): User {
            val previousUsers = db.userDao().getAll()
            if (previousUsers.isNotEmpty()) {
                return previousUsers.last()
            }
            return User(null,"","")     //üres user
        }

        override fun onPostExecute(result: User) {
            super.onPostExecute(result)
            callback.invoke(result)
        }
    }

    interface OnFragmentInteractionListener{
        fun registerNow()
        fun loginSuccess(userData : UserData)
    }
}
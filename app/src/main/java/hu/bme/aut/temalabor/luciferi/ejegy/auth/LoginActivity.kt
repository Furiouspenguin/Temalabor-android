package hu.bme.aut.temalabor.luciferi.ejegy.auth

import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.fragments.LoginFragment
import hu.bme.aut.temalabor.luciferi.ejegy.auth.fragments.RegisterFragment
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserData
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.MainActivity
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import hu.bme.aut.temalabor.luciferi.ejegy.room.AppDatabase
import hu.bme.aut.temalabor.luciferi.ejegy.room.User
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity


class LoginActivity : AppCompatActivity(), LoginFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener {
    override fun loginNow() {
        onBackPressed()
    }

    override fun registerSuccess(userData : UserData) {
        /*startActivity<MainActivity>(
            "id" to userData.id,
            "email" to userData.email,
            "name" to userData.name,
            "idCard" to userData.idCard,
            "type" to userData.type)*/
        //RestApiRepository.setUserData(userData)
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    override fun registerNow() {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.login_fragment,RegisterFragment())
            .commit()
    }

    override fun loginSuccess(userData : UserData) {
        //SET THE DATA IN THE REPOSITORY
        //RestApiRepository.setUserData(userData)

        //startActivity<MainActivity>()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

    //private lateinit var db : AppDatabase
    //private var emptyUserList : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imageView.setColorFilter(Color.argb(50,200,200,200))
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.login_fragment,LoginFragment())
            .commit()


        //Room adatkezelés
        /*db = AppDatabase(this)

        //ha már egyszer bejelentkezett a felhasználó, akkor jelentkezzünk be neki egyből
        //mivel az SQL-kezelés hosszabb időt vehet igénybe, így másik szálon indítjuk.
        //ahhoz hogy adatot kaphassunk vissza a szálról, egy interface-t valósítunk meg,
        //amellyel meghívhatunk egy függvényt a szálon zajló folyamat végeztével (itt: beírjuk az adatokat és bejelentkezünk)

        AsyncGetDataFromRoom(db) { user ->
            if (!user.email.isNullOrEmpty() and !user.password.isNullOrEmpty()) {
                emptyUserList = false
                username.setText(user.email)
                password.setText(user.password)

                loading.visibility = View.VISIBLE
                //TODO: DO THE ACTUAL LOGING IN
            }
        }.execute()*/
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}


/*private class AsyncGetDataFromRoom(val db : AppDatabase, val callback : (User) -> Unit) : AsyncTask<User, Unit, User>() {
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
}*/
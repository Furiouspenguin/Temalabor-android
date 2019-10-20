package hu.bme.aut.temalabor.luciferi.ejegy.auth

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.fragments.LoginFragment
import hu.bme.aut.temalabor.luciferi.ejegy.auth.fragments.RegisterFragment
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.MainActivity
import hu.bme.aut.temalabor.luciferi.ejegy.room.AppDatabase
import hu.bme.aut.temalabor.luciferi.ejegy.room.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


class LoginActivity : AppCompatActivity() {
    private lateinit var db : AppDatabase
    private var emptyUserList : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        imageView.setColorFilter(Color.argb(150,200,200,200))
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.login_fragment,RegisterFragment())
            .commit()

        //login backend elérése
        /*MyAsyncLogIn(username,password){result ->
            if (result.toInt() != 302){
                throw Exception("Unauthorized")
            }
        }.execute()*/

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


private class AsyncGetDataFromRoom(val db : AppDatabase, val callback : (User) -> Unit) : AsyncTask<User, Unit, User>() {
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



private class MyAsyncLogIn(val username : String,val  password : String, val callback : (String) -> Unit) : AsyncTask<String, Unit, String>(){
    override fun doInBackground(vararg p0: String): String {

        try {
            val url = URL("https://temalabor2019.azurewebsites.net/api/auth/login")
            val conn = url.openConnection() as HttpURLConnection
            conn.apply {
                requestMethod = "POST"
                doOutput = true
                useCaches = false
                outputStream.write("email=$username&password=$password".toByteArray())
                outputStream.flush()
            }
            return conn.responseCode.toString()
        } catch (e : IOException){
            e.printStackTrace()
        }
        return "ERROR"
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        callback.invoke(result)
    }
}
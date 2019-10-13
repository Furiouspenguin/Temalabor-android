package hu.bme.aut.temalabor.luciferi.ejegy.ui.login

import android.app.Activity
import android.content.Intent
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
import hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation.MainActivity
import hu.bme.aut.temalabor.luciferi.ejegy.room.AppDatabase
import hu.bme.aut.temalabor.luciferi.ejegy.room.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


//TODO: A SZERVERRE CSAK AKKOR KELLJEN ÚJRA BEJELENTKEZNI, HA LEÁLLÍTJUK AZ APPOT!
// TEHÁT AZ onStop()-BAN KELL CSAK KIJELENTKEZNI, AZ onPause()-BAN NEM!!!

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    private lateinit var db : AppDatabase
    private var emptyUserList : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(hu.bme.aut.temalabor.luciferi.ejegy.R.layout.activity_login)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }
            setResult(Activity.RESULT_OK)

            //Complete and destroy login activity once successful
            finish()
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString())
            }
        }

        db = AppDatabase(this)

        //ha már egyszer bejelentkezett a felhasználó, akkor jelentkezzünk be neki egyből
        //mivel az SQL-kezelés hosszabb időt vehet igénybe, így másik szálon indítjuk.
        //ahhoz hogy adatot kaphassunk vissza a szálról, egy interface-t valósítunk meg,
        //amellyel meghívhatunk egy függvényt a szálon zajló folyamat végeztével (itt: beírjuk az adatokat és bejelentkezünk)

        MyAsyncTask(db, object : MyAsyncTask.AsyncResponse{
            override fun processFinish(user: User?) {
                if (user != null){
                    if (!user.email.isNullOrEmpty() and !user.password.isNullOrEmpty()) {
                        emptyUserList = false
                        username.setText(user.email)
                        password.setText(user.password)

                        loading.visibility = View.VISIBLE
                        loginViewModel.login(username.text.toString(), password.text.toString())
                    }
                }
            }
        }).execute()
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(hu.bme.aut.temalabor.luciferi.ejegy.R.string.welcome)
        val displayName = model.displayName

        // TODO : initiate successful logged in experience
        //starting the MainActivity
        startActivity(Intent(this,MainActivity::class.java))

        if (emptyUserList){
            //Coroutine használatával másik szálon beírjuk a listába a jelenlegi felhasználót (mivel az eddig üres volt)
            GlobalScope.launch {
                db.userDao().add(User(email = username.text.toString(),password = password.text.toString()))
            }
        }

        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
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

private class MyAsyncTask(val db : AppDatabase) : AsyncTask<Unit, Unit, User>() {
    override fun doInBackground(vararg params: Unit?): User {
        val previousUsers = db.userDao().getAll()
        if (previousUsers.isNotEmpty()) {
            return previousUsers.last()
        }
        return User(null,"","")     //üres user
    }

    interface AsyncResponse {
        fun processFinish(user: User?)
    }

    var delegate: AsyncResponse? = null

    constructor(db : AppDatabase, delegate: AsyncResponse) : this(db) {
        this.delegate = delegate
    }

    override fun onPostExecute(result: User?) {
        super.onPostExecute(result)
        delegate?.processFinish(result)
    }
}
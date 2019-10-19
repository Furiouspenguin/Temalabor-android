package hu.bme.aut.temalabor.luciferi.ejegy.data

import android.widget.Toast
import hu.bme.aut.temalabor.luciferi.ejegy.data.model.LoggedInUser
import okhttp3.*
import java.io.IOException
import java.util.logging.Handler
import java.util.logging.LogRecord

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {


    fun login(username: String, password: String): Result<LoggedInUser> {
        try {


            // TODO: handle loggedInUser authentication
            //EZEKET A SZERVERTŐL KELLENE LEKÉRDEZNI MAJD, DE HOGY LÁTSZÓDJON A BEJELENTKEZÉS:
            val user = LoggedInUser(username,username.substringBefore('@'))
            //val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")




            return Result.Success(user)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}


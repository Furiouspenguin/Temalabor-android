package hu.bme.aut.temalabor.luciferi.ejegy.data

import hu.bme.aut.temalabor.luciferi.ejegy.data.model.LoggedInUser
import java.io.IOException

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


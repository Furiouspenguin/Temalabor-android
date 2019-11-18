package hu.bme.aut.temalabor.luciferi.ejegy.bottom_navigation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import hu.bme.aut.temalabor.luciferi.ejegy.R
import hu.bme.aut.temalabor.luciferi.ejegy.auth.retrofit.model.UserData
import hu.bme.aut.temalabor.luciferi.ejegy.repositories.RestApiRepository
import org.jetbrains.anko.longToast


//TODO: REPOSITORY + LIVEDATA KELL!!!!!!!!!!

//TODO: ez legyen az indító és ha ezen teszteljük hogy be vagyunk-e jelentkezve (getUser() hívás ha rossz, akkor loginra küld)

class MainActivity : AppCompatActivity() {

    var userData : UserData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_store, R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController,appBarConfiguration)
        navView.setupWithNavController(navController)

        userData = RestApiRepository.getUserData().value
    }
}

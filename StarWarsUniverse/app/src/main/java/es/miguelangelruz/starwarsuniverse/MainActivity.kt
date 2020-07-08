package es.miguelangelruz.starwarsuniverse

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.preference.PreferenceManager
import es.miguelangelruz.starwarsuniverse.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    //Obtenemos el NavController
    val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }

    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

}
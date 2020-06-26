package es.iessaladillo.miguelangelruz.androrecetas.ui.main

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener

class MainActivity : AppCompatActivity(), OnToolbarAvailableListener {

    //Obtenemos el NavController
    val navController: NavController by lazy {
        findNavController(R.id.navHostFragment)
    }
    //Configuración de la Appbar
    private val appBarConfiguration: AppBarConfiguration =
        AppBarConfiguration.Builder(R.id.dashboardDestination).build()

    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }

    //**Métodos que sobreescribimos al implementar OnToolbarAvailableListener**
    //Indicamos que la toolbar será proporcionada por cada fragmento
    override fun onToolbarCreated(toolbar: Toolbar) {
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
    override fun onToolbarDestroyed() {}

    override fun onResume() {
        super.onResume()
        //Si no hay nada, coge true por defecto. Acto seguido se pone false, y ya nunca más se mostrará
        if (settings.getBoolean("FirstExecution", true)){
            navController.navigate(R.id.acknowledgementDestination)
            settings.edit().putBoolean("FirstExecution", false).apply()
        }
    }
}

package es.iessaladillo.pedrojoya.pr05_trivial.ui.main

import ConfirmationDialogFragment
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import es.iessaladillo.pedrojoya.pr05_trivial.R
import es.iessaladillo.pedrojoya.pr05_trivial.ui.game.GameFragment
import es.iessaladillo.pedrojoya.pr05_trivial.ui.title.TitleFragment
import kotlinx.android.synthetic.main.game_fragment.*

class MainActivity: AppCompatActivity()  {

    private val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        //Si no hay ningun estado guardado en la actividad, navegamos al destino que marquemos como inicial
        if (savedInstanceState == null) {
            navigateToInitialDestination()
        }
    }

    //Reemplazamos el hueco por el fragmento de título
    private fun navigateToInitialDestination() {
        supportFragmentManager.commit {
            replace(R.id.fcContent, TitleFragment.newInstance())
        }
    }

    //Indicamos que en icono de la flecha "atrás" de la appbar realmente sea ir hacia atrás
    //Por defecto tiene otra funcionalidad (ir hacia arriba) pero nosotros queremos que vaya hacia atrás
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //Indicamos qué ocurre al pulsar atrás.
    //Si estamos en GameFragment y el usuario pone a true la confirmación en preferencias, se muestra el diálogo de confirmación. Si no, el comportamiento es el habitual
    override fun onBackPressed() {
        //Hallamos si se está mostrando GameFragment
        val gameFragmentIsShowing: Boolean = supportFragmentManager.findFragmentById(R.id.fcContent) is GameFragment
        //Hallamos si el usuario ha marcado en preferencias la casilla de confirmación
        val confirmationSwitchIsChecked = getConfirmationPreference()

        if (gameFragmentIsShowing && confirmationSwitchIsChecked) {
            showConfirmationDialog()
        } else {
            super.onBackPressed()
        }
    }

    //Muestra el diálogo
    private fun showConfirmationDialog() {
        ConfirmationDialogFragment()
            .show(supportFragmentManager, "ConfirmationDialog")
    }

    //Halla si el usuario ha marcado la casilla de confirmación en preferencias
    private fun getConfirmationPreference(): Boolean {
        return settings.getBoolean(
            getString(R.string.switchConfirmation),
            resources.getBoolean(R.bool.confirmationValue)
        )
    }

}
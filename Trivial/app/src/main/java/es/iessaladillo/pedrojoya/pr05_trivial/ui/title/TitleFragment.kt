package es.iessaladillo.pedrojoya.pr05_trivial.ui.title

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import es.iessaladillo.pedrojoya.pr05_trivial.R
import es.iessaladillo.pedrojoya.pr05_trivial.ui.settings.SettingsFragment
import es.iessaladillo.pedrojoya.pr05_trivial.ui.about.AboutFragment
import es.iessaladillo.pedrojoya.pr05_trivial.ui.game.GameFragment
import es.iessaladillo.pedrojoya.pr05_trivial.ui.rules.RulesFragment
import kotlinx.android.synthetic.main.title_fragment.*

class TitleFragment : Fragment(R.layout.title_fragment) {

    companion object {
        fun newInstance() = TitleFragment()
    }

    //Tenemos que sobreescribir obligatoriamente el onCreate para indicar que el fragmento tendrá items de menú propios
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Indicamos que sí tiene menu propio
        setHasOptionsMenu(true)
    }

    //Cuando la actividad se crea
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Modificamos el setupViews y ponemos setupAppBar, porque realmente lo que vamos a configurar es el appBar
        setupAppBar()
        setupViews()
    }

    //Inflamos el menu. En el caso de los fragmentos el inflater ya nos lo dan
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //Aunque ya nos den el inflater, para inflar es exactamente igual que en la actividad.
        requireActivity().menuInflater.inflate(R.menu.title_fragment, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    //Indica qué funcionalidad tiene cada item de menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.title_mnuRules -> navigateToRules()
            R.id.title_mnuAbout -> navigateToAbout()
            R.id.title_mnuSettings -> navigateToSettings()
            else -> return super.onOptionsItemSelected(item)
        }
        return true

    }

    //Para navegar hacia RulesFragment. Lo que hay que hacer es reemplazar el layout del main activity por el del fragmento
    private fun navigateToRules() {
        //Necesitamos pedir una actividad para poder llamar a supportFragmentManager. Lo hacemos con requireActivity
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcContent, RulesFragment.newInstance())
            //Le indicamos que ese replace lo agregue a la backstack.
            //Esto sirve para que cuando le demos atras, el fragmento mostrado se aparte y vuelva a aparecer TitleFragment
            addToBackStack(null)
        }
    }

    private fun navigateToAbout() {
        //Necesitamos pedir una actividad para poder llamar a supportFragmentManager. Lo hacemos con requireActivity
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcContent, AboutFragment.newInstance())
            //Le indicamos que ese replace lo agregue a la backstack.
            //Esto sirve para que cuando le demos atras, el fragmento mostrado se aparte y vuelva a aparecer TitleFragment
            addToBackStack(null)
        }
    }

    private fun navigateToSettings() {
        //Necesitamos pedir una actividad para poder llamar a supportFragmentManager. Lo hacemos con requireActivity
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcContent, SettingsFragment())
            //Le indicamos que ese replace lo agregue a la backstack.
            //Esto sirve para que cuando le demos atras, el fragmento mostrado se aparte y vuelva a aparecer TitleFragment
            addToBackStack(null)
        }
    }

    private fun setupAppBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            //Para que NO aparezca el icono de atrás. Por defecto no sale, pero si en otro fragmento lo hemos activado entonces al pulsar atrás, el icono se mantiene. Hay que indicarle que no lo haga
            setDisplayHomeAsUpEnabled(false)
            //Para que aparezca el título que quiero mostrar
            setTitle(R.string.app_name)
        }

    }

    //Se le da funcionalidad a las vistas
    private fun setupViews() {
        btnPlay.setOnClickListener {
            navigateToGame()
        }
    }

    private fun navigateToGame() {
        //Necesitamos pedir una actividad para poder llamar a supportFragmentManager. Lo hacemos con requireActivity
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcContent, GameFragment.newInstance())
            addToBackStack(null)
        }
    }

}



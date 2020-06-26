package es.iessaladillo.pedrojoya.pr05_trivial.ui.game.game_over


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

import es.iessaladillo.pedrojoya.pr05_trivial.R
import es.iessaladillo.pedrojoya.pr05_trivial.ui.game.GameFragment
import kotlinx.android.synthetic.main.game_over_fragment.*

class GameOverFragment : Fragment(R.layout.game_over_fragment) {


    companion object {
        fun newInstance() = GameOverFragment()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        //modificamos la action bar
        setupAppBar()

    }

    private fun setupAppBar() {
        (requireActivity() as AppCompatActivity).supportActionBar?.run {
            //Para que aparezca el icono de atrás
            //Esto solo habilita el icono, pero no va hacia atrás.
            //Para que al pulsar vaya a atrás, hay que sobreescribir el método onSupportNavigateUp() EN LA ACTIVIDAD, indicándole que haga onBackPressed()
            setDisplayHomeAsUpEnabled(true)
            //Para que aparezca el título que quiero mostrar
            setTitle(R.string.game_over_title)
        }
    }

    //Le damos fucionalidad al botón. Si se pulsa, se dirige a GameFragment
    private fun setupViews() {
        btn_try_again.setOnClickListener {
            navigateToGameFragment()
        }
    }


    private fun navigateToGameFragment() {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcContent, GameFragment.newInstance())
        }
    }


}

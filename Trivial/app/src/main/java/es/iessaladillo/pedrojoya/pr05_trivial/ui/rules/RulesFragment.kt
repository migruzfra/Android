package es.iessaladillo.pedrojoya.pr05_trivial.ui.rules


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity

import es.iessaladillo.pedrojoya.pr05_trivial.R


class RulesFragment : Fragment(R.layout.rules_fragment) {

    companion object {
        fun newInstance() = RulesFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
            setTitle(R.string.rules_title)
        }
    }

}

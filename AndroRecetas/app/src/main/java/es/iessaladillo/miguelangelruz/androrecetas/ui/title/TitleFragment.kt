package es.iessaladillo.miguelangelruz.androrecetas.ui.title

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.title_fragment.*

import es.iessaladillo.miguelangelruz.androrecetas.R

class TitleFragment : Fragment(R.layout.title_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        clRoot.setOnClickListener {
            navigateToDashboard()
        }
        lblButtonStart.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.navigateToDashboard)
    }

}

package es.iessaladillo.miguelangelruz.androrecetas.ui.acknowledgement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import es.iessaladillo.miguelangelruz.androrecetas.R
import kotlinx.android.synthetic.main.acknowledgement_fragment.*

class AcknowledgementFragment : Fragment(R.layout.acknowledgement_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        clRoot.setOnClickListener { findNavController().navigate(R.id.profileDestination) }
    }

}

package es.miguelangelruz.starwarsuniverse.ui.main

import android.graphics.Typeface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.miguelangelruz.starwarsuniverse.R
import es.miguelangelruz.starwarsuniverse.data.SwapiApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(R.layout.main_fragment)  {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        clroot.setOnClickListener {
            navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.navigateToCharacters)
    }
}
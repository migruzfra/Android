package es.miguelangelruz.starwarsuniverse.ui.characters.character

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import es.miguelangelruz.starwarsuniverse.R
import es.miguelangelruz.starwarsuniverse.ui.characters.CharactersViewModel
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.character_fragment.*

class CharacterFragment : Fragment(R.layout.character_fragment) {

    var characterId by Delegates.notNull<Long>()
    private val viewModel: CharacterViewModel by viewModels()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = CharacterFragmentArgs.fromBundle(requireArguments())
        characterId = args.characterId
        //viewModel.getCharacter(characterId) quitar luego la variable de clase y ponerlo solo llamando al viewmodel
    }

    private fun setupViews() {
        prueba.text = characterId.toString()
    }



}
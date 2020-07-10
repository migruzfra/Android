package es.miguelangelruz.starwarsuniverse.ui.characters.character

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import es.miguelangelruz.starwarsuniverse.R
import es.miguelangelruz.starwarsuniverse.data.response.CharacterResponse
import kotlinx.android.synthetic.main.character_fragment.*

class CharacterFragment : Fragment(R.layout.character_fragment) {

    private val viewModel: CharacterViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = CharacterFragmentArgs.fromBundle(requireArguments())
        getCharacter(args.characterId)
    }

    private fun setupViews() {
        observeLiveData()
    }

    private fun getCharacter(characterId: Long) {
        viewModel.getCharacter(characterId)
    }

    private fun observeLiveData() {
        observeCharacter()
        observeListIsCompleted()
    }

    private fun observeCharacter() {
        viewModel.character.observe(viewLifecycleOwner, Observer {
            showCharacter(it)
        })
    }

    private fun observeListIsCompleted() {
        viewModel.characterIsCompleted.observe(viewLifecycleOwner, Observer {
            if (it) {
                hideLoadingAnimation()
            } else {
                showLoadingAnimation()
            }
        })
    }

    private fun showCharacter(character: CharacterResponse) {
        name.text = getString(R.string.character_name, character.name)
        height.text = getString(R.string.character_height, character.height)
        weight.text = getString(R.string.character_weight, character.mass)
        hair_color.text = getString(R.string.character_hair_color, character.hairColor)
        birth_year.text = getString(R.string.character_birth_year, character.birthYear)
        gender.text = getString(R.string.character_gender, character.gender)
        homeworld.text = getString(R.string.character_homeworld, character.homeworld)
        films.text = getString(R.string.character_films, character.films)
        name.text = character.name
        name.text = character.name
        name.text = character.name
    }

    private fun showLoadingAnimation() {
        layer_translucent.visibility = View.VISIBLE
        gif_loading.visibility = View.VISIBLE
    }

    private fun hideLoadingAnimation() {
        layer_translucent.visibility = View.INVISIBLE
        gif_loading.visibility = View.INVISIBLE
    }

}
package es.miguelangelruz.starwarsuniverse.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.miguelangelruz.starwarsuniverse.R
import es.miguelangelruz.starwarsuniverse.ui.characters.CharactersListAdapter
import es.miguelangelruz.starwarsuniverse.ui.characters.CharactersViewModel
import kotlinx.android.synthetic.main.characters_fragment.*

class CharactersFragment : Fragment(R.layout.characters_fragment) {

    private val viewModel: CharactersViewModel by viewModels()
    private val listAdapter: CharactersListAdapter =
        CharactersListAdapter().also {
            it.onItemClicked = { position ->
                navigateToCharacter(getCharacterId(position))
            }
        }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        observeLiveData()
        setupListAdapter()
        getCharacters()
    }

    private fun getCharacters() {
        viewModel.getCharacters()
    }

    private fun setupListAdapter() {
        recycler_view.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }

    private fun observeLiveData() {
        observeListCharacters()
        observeListIsCompleted()
    }

    private fun observeListCharacters() {
        viewModel.listCharacters.observe(viewLifecycleOwner, Observer { submitList() })
    }

    private fun observeListIsCompleted() {
        viewModel.listIsCompleted.observe(viewLifecycleOwner, Observer {
            if (it) {
                hideLoadingAnimation()
            }
        })
    }

    private fun submitList() {
        listAdapter.submitList(viewModel.listCharacters.value)
    }

    private fun hideLoadingAnimation() {
        layer_translucent.visibility = View.INVISIBLE
        gif_loading.visibility = View.INVISIBLE
    }

    private fun getCharacterId(position: Int) : Long {
        val regex = Regex("\\d+")
        val match = regex.find(viewModel.listCharacters.value!![position].url)
        return match?.groups?.first()?.value!!.toLong()
    }

    private fun navigateToCharacter(characterId: Long) {
        val action = CharactersFragmentDirections.navigateToCharacter(characterId)
        findNavController().navigate(action)
    }

}
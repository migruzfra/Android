package es.iessaladillo.miguelangelruz.androrecetas.ui.search.ranking

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Step
import es.iessaladillo.miguelangelruz.androrecetas.extensions.hideSoftKeyboard
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.ranking_fragment.*
import kotlin.properties.Delegates

class RankingFragment : Fragment(R.layout.ranking_fragment) {

    private val listAdapter: RankingFragmentAdapter =
        RankingFragmentAdapter()
            .also {
                //Al hacer click en un item llamaremos al método para viajar a la receta, pasándole por parámetro el id de la receta
                it.onItemClickListener =
                    { position -> navigateToRecipe(viewModel.listRecipes[position].recipe.codeRecipe) }
            }


    private val viewModel: RankingViewModel by viewModels {
        RankingViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            requireActivity().application
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupRecyclerView()
        setupToolbar()
    }

    private fun setupRecyclerView() {
        recycler_view.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
        submitList()
    }

    private fun submitList() {
        listAdapter.submitList(viewModel.listRecipes.map { recipe -> recipe.recipe.nameRecipe })
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.help_menu_b)
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        setupButtonsToolbar()
    }

    private fun setupButtonsToolbar() {
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.help) {
                setupHelpButton()
            }
            true
        }
    }

    private fun setupHelpButton() {
        MessageDialog(getString(R.string.help_title), getString(R.string.help_ranking_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }


    private fun navigateToRecipe(codeRecipe: Long) {
        val action = RankingFragmentDirections.navigateToOverview(codeRecipe)
        findNavController().navigate(action)
    }

}

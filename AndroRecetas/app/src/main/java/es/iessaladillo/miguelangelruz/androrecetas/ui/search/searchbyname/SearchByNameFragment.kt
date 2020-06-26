package es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchbyname

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.search_by_name_fragment.*

class SearchByNameFragment: Fragment(R.layout.search_by_name_fragment) {

    private lateinit var currentList : List<RecipeWithSteps>
    private val viewModel: SearchByNameViewModel by viewModels {
        SearchByNameViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            requireActivity().application
        )
    }

    private val listAdapter: SearchByNameFragmentAdapter = SearchByNameFragmentAdapter().also {
        //Al hacer click en un item llamaremos al método para viajar a la receta, pasándole por parámetro el id de la receta
        it.onItemClicked = { position ->
            navigateToRecipe(currentList[position].recipe.codeRecipe)
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            currentList = viewModel.getRecipesByName("%" + lblSearch.text.toString() + "%")
            if (currentList.size > 0) {
                showListRecipes()
                showRecyclerView()
            } else {
                hideRecyclerView()
            }

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
        setupButtons()
        setupRecyclerView()
    }

    private fun setupButtons() {
        lblSearch.addTextChangedListener(textWatcher)
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.help_menu)
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        setupButtonsToolbar()
    }



    private fun showListRecipes() {
        recycler_view.post {
            listAdapter.submitList(currentList.map { recipeWithSteps -> recipeWithSteps.recipe.nameRecipe })
        }
    }

    private fun setupRecyclerView() {
        recycler_view.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
    }

    private fun showRecyclerView() {
        error_message.visibility = View.INVISIBLE
        recycler_view.visibility = View.VISIBLE
    }

    private fun hideRecyclerView() {
        recycler_view.visibility = View.INVISIBLE
        error_message.visibility = View.VISIBLE
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_searchbyname_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }

    private fun navigateToRecipe(codeRecipe: Long) {
        val action = SearchByNameFragmentDirections.navigateToOverview(codeRecipe)
        findNavController().navigate(action)
    }

}
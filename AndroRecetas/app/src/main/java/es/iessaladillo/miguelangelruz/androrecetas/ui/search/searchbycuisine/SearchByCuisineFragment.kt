package es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchbycuisine

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.data.local.enum.Cuisine
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchbygroup.SearchByGroupFragmentDirections
import kotlinx.android.synthetic.main.search_by_cuisine_fragment.*

class SearchByCuisineFragment : Fragment(R.layout.search_by_cuisine_fragment) {

    val allRecipes by lazy { viewModel.recipeDao.queryAllRecipesWithSteps() }
    lateinit var mapCuisineId: Map<String, List<Long>>
    val cuisinesIds = Cuisine.values().map { cuisine -> cuisine.cuisine }

    private val viewModel: SearchByCuisineViewModel by viewModels {
        SearchByCuisineViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            requireActivity().application
        )
    }

    private val listAdapter: SearchByCuisineFragmentAdapter =
        SearchByCuisineFragmentAdapter().also {
            //Al hacer click en un item llamaremos al método para viajar a la receta, pasándole por parámetro el id de la receta
            it.onItemClicked =
                { position -> navigateToRecipe(getCodeRecipeFromItemPosition(position)) }
        }

    private fun getCodeRecipeFromItemPosition(position: Int) =
        mapCuisineId[cuisinesIds[spinner.selectedItemPosition]]?.get(position)!!


    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
        setSpinner()
    }

    private fun setSpinner() {
        //Obtenemos la cocina que mostrará el spinner a partir de los valores del enum Cuisine
        val cuisines = Cuisine.values().map{ cuisine -> getString(cuisine.cuisineResId)}.toTypedArray()

        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, cuisines
        )

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                setupRecyclerView(cuisinesIds[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    private fun setupRecyclerView(cuisine: String) {

        val mapCuisineName = allRecipes.groupBy(keySelector = { it.recipe.cuisine },
            valueTransform = { it.recipe.nameRecipe })
        mapCuisineId = allRecipes.groupBy(keySelector = { it.recipe.cuisine },
            valueTransform = { it.recipe.codeRecipe })

        if (mapCuisineName.containsKey(cuisine)) {
            recycler_view.post {
                listAdapter.submitList(mapCuisineName[cuisine])
            }
            showRecyclerView()
        } else {
            hideRecyclerView()
        }


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

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.help_menu_b)
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        setupButtons()
    }

    private fun setupButtons() {
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.help) {
                setupHelpButton()
            }
            true
        }
    }

    private fun setupHelpButton() {
        MessageDialog(getString(R.string.help_title), getString(R.string.help_searchbycuisine_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }

    private fun navigateToRecipe(codeRecipe: Long) {
        val action = SearchByGroupFragmentDirections.navigateToOverview(codeRecipe)
        findNavController().navigate(action)
    }

}

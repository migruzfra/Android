package es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchbygroup

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
import es.iessaladillo.miguelangelruz.androrecetas.data.local.enum.Group
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.search_by_group_fragment.*

class SearchByGroupFragment : Fragment(R.layout.search_by_group_fragment) {

    val allRecipes by lazy { viewModel.recipeDao.queryAllRecipesWithSteps() }
    lateinit var mapGroupId: Map<String, List<Long>>
    val groupsIds = Group.values().map { group -> group.group }

    private val viewModel: SearchByGroupViewModel by viewModels {
        SearchByGroupViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            requireActivity().application
        )
    }

    private val listAdapter: SearchByGroupFragmentAdapter = SearchByGroupFragmentAdapter().also {
        //Al hacer click en un item llamaremos al método para viajar a la receta, pasándole por parámetro el id de la receta
        it.onItemClicked = { position ->
            navigateToRecipe(getCodeRecipeFromItemPosition(position))
        }
    }

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
        //Obtenemos los grupos de alimentos que mostrará el spinner a partir de los valores del enum Group
        val groupsResId = Group.values().map { group -> getString(group.groupResId) }.toTypedArray()
        val groupDescriptions = Group.values().map { group -> getString(group.groupDescrResId) }.toTypedArray()

        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, groupsResId
        )

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            //view es View? porque si no, al pulsar back provoca IllegalArgumentException
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                //Al cambiar el item del spinner se cambia el recycler view y la descripción
                setupRecyclerView(groupsIds[position])
                setGroupDescription(groupDescriptions)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

    private fun setGroupDescription(groupDescriptions: Array<String>) {
        lblGroupDesription.text = groupDescriptions[spinner.selectedItemPosition]
    }

    private fun setupRecyclerView(group: String) {

        val mapGroupName = allRecipes.groupBy(keySelector = { it.recipe.group },
            valueTransform = { it.recipe.nameRecipe })
        mapGroupId = allRecipes.groupBy(keySelector = { it.recipe.group },
            valueTransform = { it.recipe.codeRecipe })

        if (mapGroupName.containsKey(group)) {
            recycler_view.post {
                listAdapter.submitList(mapGroupName[group])
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_searchbygroup_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }

    private fun navigateToRecipe(codeRecipe: Long) {
        val action = SearchByGroupFragmentDirections.navigateToOverview(codeRecipe)
        findNavController().navigate(action)
    }

    private fun getCodeRecipeFromItemPosition(position: Int) =
        mapGroupId[groupsIds[spinner.selectedItemPosition]]?.get(position)!!
}


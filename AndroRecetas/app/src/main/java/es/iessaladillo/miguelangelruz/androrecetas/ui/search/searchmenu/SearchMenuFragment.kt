package es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchmenu

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
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
import es.iessaladillo.miguelangelruz.androrecetas.data.local.entity.DashboardOption
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.search_menu_fragment.*
import kotlinx.android.synthetic.main.search_menu_fragment.toolbar

class SearchMenuFragment : Fragment(R.layout.search_menu_fragment) {

    private val viewModel: SearchMenuViewModel by viewModels {
        SearchMenuViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            requireActivity().application
        )
    }

    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        deleteJunkRecipes()
    }

    private fun setupViews() {
        setupToolbar()
        setupFonts()
        setupButtons()
    }

    private fun setupButtons() {
        lbl_random_recipe.setOnClickListener { goToRandomRecipe() }
        lbl_ranking.setOnClickListener {  }
        lbl_search_by_cuisine.setOnClickListener { findNavController().navigate(R.id.navigateToSearchByCuisine) }
        lbl_search_by_group.setOnClickListener { findNavController().navigate(R.id.navigateToSearchByGroup) }
        lbl_search_by_name.setOnClickListener { findNavController().navigate(R.id.navigateToSearchByName) }
        img_ranking.setOnClickListener { findNavController().navigate(R.id.navigateToRanking) }
        lbl_ranking.setOnClickListener { findNavController().navigate(R.id.navigateToRanking) }
    }
    private fun setupFonts() {
        lbl_random_recipe.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
        lbl_ranking.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
        lbl_search_by_cuisine.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
        lbl_search_by_group.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
        lbl_search_by_name.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_searchmenu_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }

    private fun goToRandomRecipe() {
        val randomIdRecipe = viewModel.queryListIdRecipes().random()
        val action = SearchMenuFragmentDirections.navigateToOverview(randomIdRecipe)
        findNavController().navigate(action)
    }

    private fun deleteJunkRecipes() {
        viewModel.deleteRecipesWithoutSteps()
        viewModel.deleteJunkRecipes()
    }

}

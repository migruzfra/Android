package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addRecipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.base.observeEvent
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Recipe
import es.iessaladillo.miguelangelruz.androrecetas.data.local.enum.Cuisine
import es.iessaladillo.miguelangelruz.androrecetas.data.local.enum.Group
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addRecipe.AddRecipeFragmentDirections
import kotlinx.android.synthetic.main.add_recipe_fragment.*

class AddRecipeFragment : Fragment(R.layout.add_recipe_fragment) {

    val groupsIds = Group.values().map { group -> group.group }
    val cuisineIds = Cuisine.values().map { cuisine -> cuisine.cuisine }
    private val viewModel: AddRecipeViewModel by viewModels {
        AddRecipeViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            requireActivity().application
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupSpinners()
        setupButtons()
        observeLiveData()
        setupToolbar()
    }

    private fun observeLiveData() {
        viewModel.message.observeEvent(this) {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupSpinners() {
        setupSpinnerGroup()
        setupSpinnerCuisine()
    }

    private fun setupSpinnerGroup() {
        val groups = Group.values().map { group -> getString(group.groupResId) }.toTypedArray()

        spinnerGroup.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, groups
        )
    }

    private fun setupSpinnerCuisine() {
        val cuisines = Cuisine.values().map { cuisine -> getString(cuisine.cuisineResId) }.toTypedArray()

        spinnerCuisine.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, cuisines
        )
    }

    private fun setupButtons() {
        btnGoToSteps.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        if (allFieldsValid()) {
            insertRecipe()
        } else {
            requestFocusWhereItBelongs()
            viewModel.errorValidationMessage()
        }
    }

    private fun allFieldsValid(): Boolean {
        return txtName.text.toString().isNotBlank() && txtDescription.text.toString().isNotBlank()
    }

    private fun requestFocusWhereItBelongs() {
        if (txtName.text.toString().isBlank()) {
            txtName.requestFocus()
        } else {
            txtDescription.requestFocus()
        }
    }

    private fun insertRecipe() {
        val codeRecipeInserted = viewModel.insertRecipe(
            Recipe(
                0,
                txtName.text.toString(),
                txtDescription.text.toString(),
                groupsIds[spinnerGroup.selectedItemPosition],
                viewModel.getCurrentUser(),
                cuisineIds[spinnerCuisine.selectedItemPosition],
                0f,
                0,
                ""
            )
        )
        navigateToAddSteps(codeRecipeInserted)
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.help_menu)
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_addrecipe_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }


    private fun navigateToAddSteps(codeRecipeInserted: Long) {
        val action =
            AddRecipeFragmentDirections.navigateToAddIngredients(
                codeRecipeInserted
            )
        findNavController().navigate(action)
    }



}

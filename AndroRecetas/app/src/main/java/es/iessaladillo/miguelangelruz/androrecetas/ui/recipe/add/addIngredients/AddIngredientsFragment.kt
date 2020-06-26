package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addIngredients

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Step
import es.iessaladillo.miguelangelruz.androrecetas.extensions.hideSoftKeyboard
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.add_ingredients_fragment.*
import kotlin.properties.Delegates


class AddIngredientsFragment : Fragment(R.layout.add_ingredients_fragment) {

    var codeRecipe by Delegates.notNull<Long>()
    private val listAdapter: AddIngredientsFragmentAdapter =
        AddIngredientsFragmentAdapter()
            .also {
                //Al hacer click en un item llamaremos al método para viajar a la receta, pasándole por parámetro el id de la receta
                it.onItemClickListener =
                    { position -> deleteStep(position) }
            }

    private val viewModel: AddIngredientsViewModel by viewModels {
        AddIngredientsViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            requireActivity().application
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val args =
            AddIngredientsFragmentArgs.fromBundle(
                requireArguments()
            )
        codeRecipe = args.codeRecipe
    }

    private fun setupViews() {
        setupButtons()
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
        listAdapter.submitList(viewModel.listIngredients)
    }

    private fun setupButtons() {
        setupButtonAddStep()
        setupButtonFinish()
    }

    private fun setupButtonAddStep() {
        btnAddItem.setOnClickListener {
            if (txtItem.text.toString().isNotBlank()) {
                viewModel.addIngredient(txtItem.text.toString().replace(",", " "))
                txtItem.hideSoftKeyboard()
                txtItem.setText("")
                setupRecyclerView()
            } else {
                Snackbar.make(requireView(), R.string.error_add_steps_message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupButtonFinish() {
        btnGoToSteps.setOnClickListener {
            if (viewModel.listIngredients.isNotEmpty()) {
                viewModel.insertIngredients(codeRecipe)
                navigateToAddSteps(codeRecipe)
            } else {
                MessageDialog(getString(R.string.error), getString(R.string.error_enter_ingredients_message)).show(
                    requireFragmentManager(),
                    "Error"
                )
            }
        }
    }

    private fun deleteStep(position: Int) {
        viewModel.deleteIngredient(position)
        setupRecyclerView()
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_addingredients_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }

    private fun navigateToAddSteps(codeRecipeInserted: Long) {
        val action =
            AddIngredientsFragmentDirections.navigateToAddSteps(
                codeRecipeInserted
            )
        findNavController().navigate(action)
    }

}

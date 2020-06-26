package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.overview

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps
import es.iessaladillo.miguelangelruz.androrecetas.data.local.enum.Cuisine
import es.iessaladillo.miguelangelruz.androrecetas.data.local.enum.Group
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.overview_fragment.*
import kotlinx.android.synthetic.main.overview_fragment.toolbar
import kotlin.properties.Delegates

//PARA EL SAFEARGS: https://www.youtube.com/watch?v=CLzuMv0cFYg
class OverviewFragment : Fragment(R.layout.overview_fragment) {

    var codeRecipe by Delegates.notNull<Long>()
    lateinit var recipe: RecipeWithSteps
    lateinit var listStepsText : List<String>
    lateinit var listIngredients : String
    var isIngredientsShowing = false
    val cuisinesIds = Cuisine.values()
    val groupsIds = Group.values()

    private val viewModel: OverviewViewModel by viewModels {
        OverviewViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            AndroRecetasDatabase.getInstance(requireContext()).userDao,
            requireActivity().application
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = OverviewFragmentArgs.fromBundle(requireArguments())
        codeRecipe = args.codeRecipe
        recipe = viewModel.getRecipe(codeRecipe)
    }

    private fun setupViews() {
        setupToolbar()
        setupData()
        seupButtons()
    }

    private fun seupButtons() {
        img_start.setOnClickListener {
            navigateToRecipe()
        }
        img_ingredients.setOnClickListener {
            isIngredientsShowing = !isIngredientsShowing
            showIngredients()
        }
        lbl_import_ingredients.setOnClickListener {
            ingredientsToShoppingList()
        }
        fabImportIngredients.setOnClickListener {
            ingredientsToShoppingList()
        }
    }

    private fun ingredientsToShoppingList() {
        viewModel.ingredientsToShoppingList(listIngredients)
        Snackbar.make(requireView(), R.string.snackbar_message_ingredients_imported_successfully, Snackbar.LENGTH_LONG).show()
    }

    @SuppressLint("SetTextI18n")
    private fun setupData() {
        recipe.run {
            //Datos de receta
            recipe.run {
                lblname.text = nameRecipe
                lblgroup.text = getText(R.string.overview_group_title).toString() + " " + getString(groupsIds.find { it.group == group }!!.groupResId).toLowerCase()
                lbluser.text = getText(R.string.overview_user_title).toString() + " " + viewModel.getUsernameFromId(idUser)
                imgUser.setImageResource(viewModel.getUserImgFromId(idUser))
                lblcuisine.text = getText(R.string.overview_cuisine_title).toString() + " " + getString(cuisinesIds.find { it.cuisine == cuisine }!!.cuisineResId).toLowerCase()
                lblDescription.text = descripRecipe
                list_ingredients.text = "- " + ingredients.replace(", ", "\n- ")
                //listIngredients = ingredients.splitToSequence(',').filter { it.isNotBlank() }.map { ing -> ing.trim() }.toList()
                listIngredients = ingredients
                if (totalVoters > 0) {
                    ratingStars.rating = totalPoints/totalVoters
                } else {
                    lblRating.visibility = View.VISIBLE
                    ratingStars.visibility = View.INVISIBLE
                }
            }
            //Datos de los pasos
            listStepsText = this.steps.map { step -> step.descripStep }
        }
        lblname.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
        lbl_start.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
        lbl_ingredients.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.help_menu_b)
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        setupToolbarButtons()
    }
    private fun setupToolbarButtons() {
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.help) {
                setupHelpButton()
            }
            true
        }
    }

    private fun setupHelpButton() {
        MessageDialog(getString(R.string.help_title), getString(R.string.help_overview_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }

    private fun showIngredients() {
        if (isIngredientsShowing) {
            lblDescription.visibility = View.INVISIBLE
            lblgroup.visibility = View.INVISIBLE
            lbluser.visibility = View.INVISIBLE
            imgUser.visibility = View.INVISIBLE
            lblcuisine.visibility = View.INVISIBLE
            lblRating.visibility = View.INVISIBLE
            ratingStars.visibility = View.INVISIBLE
            list_ingredients.visibility = View.VISIBLE
            lbl_import_ingredients.visibility = View.VISIBLE
            fabImportIngredients.visibility = View.VISIBLE
        } else {
            lblDescription.visibility = View.VISIBLE
            lblgroup.visibility = View.VISIBLE
            lbluser.visibility = View.VISIBLE
            imgUser.visibility = View.VISIBLE
            if (recipe.recipe.totalVoters > 0) {
                ratingStars.visibility = View.VISIBLE
            } else {
                lblRating.visibility = View.VISIBLE
            }
            lblcuisine.visibility = View.VISIBLE
            list_ingredients.visibility = View.INVISIBLE
            lbl_import_ingredients.visibility = View.INVISIBLE
            fabImportIngredients.visibility = View.INVISIBLE
        }
    }


    private fun navigateToRecipe() {
        val action = OverviewFragmentDirections.navigateToSteps(listStepsText.toTypedArray(), codeRecipe)
        findNavController().navigate(action)
    }

}

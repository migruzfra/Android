package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.end

import android.opengl.Visibility
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.end_recipe_fragment.*

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import kotlin.properties.Delegates

class EndRecipeFragment : Fragment(R.layout.end_recipe_fragment) {

    var codeRecipe by Delegates.notNull<Long>()
    private val listRecipesRated by lazy {  toList(viewModel.getCurrentUser().recipesRated)  }

    private val viewModel: EndRecipeViewModel by viewModels {
        EndRecipeViewModelFactory(
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
        val args = EndRecipeFragmentArgs.fromBundle(requireArguments())
        codeRecipe = args.codeRecipe
    }

    private fun setupViews() {
        setupButtons()
        setupRatingBar()
    }

    private fun setupRatingBar() {
        ratingStars.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            val stars = if (rating % 1 == 0f) rating.toInt().toString() else rating.toString()
            btnRate.text = getString(R.string.rate_with, stars)
        }
    }

    private fun setupLbls() {
        recipe_finished_title.text = getString(R.string.recipe_finished_title, viewModel.getRecipeName(codeRecipe).toLowerCase())
        btnDontRate.text = getString(R.string.dont_rate)
        val recipe = viewModel.getRecipe(codeRecipe).recipe
        if(recipe.totalVoters > 0) {
            ratingStars.rating = (recipe.totalPoints / recipe.totalVoters)
        }
        if (!listRecipesRated.contains(codeRecipe)) {
            val stars = if (ratingStars.rating % 1 == 0f) ratingStars.rating.toInt().toString() else ratingStars.rating.toString()
            btnRate.text = getString(R.string.rate_with, stars)
        } else {
            btnRate.visibility = View.INVISIBLE
            ratingStars.visibility = View.INVISIBLE
            recipe_already_rated.visibility = View.VISIBLE
            recipe_finished_subtitle.visibility = View.INVISIBLE
        }


    }

    private fun setupButtons() {
        setupLbls()

        btnRate.setOnClickListener {
            if (!listRecipesRated.contains(codeRecipe)) {
                listRecipesRated.add(codeRecipe)
                viewModel.updateRatingRecipe(
                    viewModel.getRecipe(codeRecipe).recipe.totalPoints + ratingStars.rating,
                    viewModel.getRecipe(codeRecipe).recipe.totalVoters + 1,
                    codeRecipe
                )
                viewModel.updateUserRecipesRated(fromList(listRecipesRated))
            }
            Snackbar.make(requireView(), R.string.snackbar_message_recipe_rated_successfully, Snackbar.LENGTH_LONG).show()
            navigateToDashboard()
        }

        btnDontRate.setOnClickListener {
            navigateToDashboard()
        }
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.dashboardDestination)
    }


    private fun fromList(nums : MutableList<Long>) : String {
        var string = ""
        for (num in nums) {
            string += (num.toString() + ",")
        }
        return string.substring(0,string.length-1)
    }

    private fun toList(concatenatedNums: String) : MutableList<Long> {
        if (concatenatedNums.isNotBlank()) {
            return concatenatedNums.split(",").map { s -> s.toLong() }.toMutableList()
        } else {
            return mutableListOf()
        }
    }

}

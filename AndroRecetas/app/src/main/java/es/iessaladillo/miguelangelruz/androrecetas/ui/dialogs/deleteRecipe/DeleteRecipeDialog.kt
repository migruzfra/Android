package es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.deleteRecipe

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.ui.main.MainActivity

class DeleteRecipeDialog(val codeRecipe: Long, val listLong: List<Long>, val recipeDao: RecipeDao) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.exit_addsteps_title)
            .setMessage(R.string.exit_addsteps_message)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteRecipe(codeRecipe)
            }
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .create()

    private fun deleteRecipe(codeRecipe: Long) {
        recipeDao.deleteRecipe(codeRecipe)
    }
}
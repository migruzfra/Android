package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addSteps

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
import kotlinx.android.synthetic.main.add_steps_fragment.*
import kotlin.properties.Delegates


class AddStepsFragment : Fragment(R.layout.add_steps_fragment) {

    var codeRecipe by Delegates.notNull<Long>()
    private val listAdapter: AddStepsFragmentAdapter =
        AddStepsFragmentAdapter()
            .also {
                //Al hacer click en un item llamaremos al método para viajar a la receta, pasándole por parámetro el id de la receta
                it.onItemClickListener =
                    { position -> deleteStep(position) }
            }

    private val viewModel: AddStepsViewModel by viewModels {
        AddStepsViewModelFactory(
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
            AddStepsFragmentArgs.fromBundle(
                requireArguments()
            )
        codeRecipe = args.codeRecipe
    }

    private fun setupViews() {
        setupButtons()
        setupRecyclerView()
        setupToolbar()
        lbl_finish.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
    }

    private fun setupRecyclerView() {
        recycler_view.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = listAdapter
        }
        submitList()
        txtItem.hint = getString(R.string.num_step_add_step, (viewModel.listSteps.size + 1).toString())
    }

    private fun submitList() {
        listAdapter.submitList(viewModel.listSteps.map { step -> step.descripStep }.toList())
    }

    private fun setupButtons() {
        setupButtonAddStep()
        setupButtonFinish()
    }

    private fun setupButtonAddStep() {
        btnAddItem.setOnClickListener {
            if (txtItem.text.toString().isNotBlank()) {
                viewModel.addStep(Step(0, txtItem.text.toString(), codeRecipe))
                txtItem.hideSoftKeyboard()
                txtItem.setText("")
                setupRecyclerView()
            } else {
                Snackbar.make(requireView(), R.string.error_add_steps_message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun setupButtonFinish() {
        btnFinish.setOnClickListener {
            if (viewModel.listSteps.isNotEmpty()) {
                viewModel.insertSteps()
                Snackbar.make(requireView(), R.string.snackbar_message_recipe_created_successfully, Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.dashboardDestination)
            } else {
                MessageDialog(getString(R.string.error), getString(R.string.error_enter_steps_message)).show(
                    requireFragmentManager(),
                    "Error"
                )
            }
        }
    }

    private fun deleteStep(position: Int) {
        viewModel.deleteStep(position)
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_addsteps_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }
    //Al destruir comprobamos si se ha insertado algún paso. Si no, la receta se elimina
    override fun onDestroy() {
        if (viewModel.listSteps.isEmpty()) viewModel.deleteRecipe(codeRecipe)
        super.onDestroy()
    }

}

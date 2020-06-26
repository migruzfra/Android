package es.iessaladillo.miguelangelruz.androrecetas.ui.dashboard

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.lifecycle.observe
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.base.SharedPreferenceLongLiveData
import es.iessaladillo.miguelangelruz.androrecetas.data.local.entity.DashboardOption
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.dashboard_fragment.*
import java.util.*

class DashboardFragment : Fragment(R.layout.dashboard_fragment) {


    private val viewModel: DashboardViewModel by viewModels {
        DashboardViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).recipeDao,
            AndroRecetasDatabase.getInstance(requireContext()).userDao,
            requireActivity().application
        )
    }

    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_USER
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
        observeUser()
        setupButtons()
        setupFonts()
    }

    private fun setupFonts() {
        lbl_profile.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
        lbl_add_recipe.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
        lbl_search.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
    }

    private fun setupButtons() {
        img_search.setOnClickListener { navigateTo(R.id.navigateToSearchMenu) }
        img_profile.setOnClickListener { navigateTo(R.id.navigateToProfile) }
        img_add_recipe.setOnClickListener { navigateTo(R.id.navigateToAddRecipe) }
    }

    private fun observeUser() {
        SharedPreferenceLongLiveData(settings, "currentPlayer", -1L).observe(viewLifecycleOwner) {
            if (it > 1L) {
                val array = resources.getStringArray(R.array.array_tips)
                lbl_tip_of_the_day.text = String.format("\"%s\"", array[(Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % array.size)])
                lbl_tip_of_the_day_title.visibility = View.VISIBLE
                lbl_tip_of_the_day_title.text = getString(R.string.tip_of_the_day_title, viewModel.getUsernameFromId(it))
                lbl_tip_of_the_day.typeface = Typeface.createFromAsset(requireContext().assets, "fonts/Andora Demo.ttf")
                img_profile.setImageResource(viewModel.getUserImgFromId(it))
            } else {
                lbl_tip_of_the_day.text = getString(R.string.dashboard_no_current_user)
                lbl_tip_of_the_day_title.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.help_settings_menu)
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        setupButtonsToolbar()
    }

    private fun setupButtonsToolbar() {
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.help) {
                setupHelpButton()
            } else if (it.itemId == R.id.settings) {
                setupSettingsButton()
            }
            true
        }
    }

    private fun setupHelpButton() {
        MessageDialog(getString(R.string.help_title), getString(R.string.help_dashboard_toolbar_text)).show(
            childFragmentManager,
            "AboutDialog"
        )
    }

    private fun setupSettingsButton() {
        findNavController().navigate(R.id.navigateToSettings)
    }

    private fun navigateTo(idAction: Int) {
        if (settings.getLong("currentPlayer", -1L) != -1L) {
            findNavController().navigate(idAction)
        } else {
            findNavController().navigate(R.id.navigateToProfile)
        }
    }
}


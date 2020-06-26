package es.iessaladillo.miguelangelruz.androrecetas.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.settings_fragment.*

class SettingsFragment : Fragment(R.layout.settings_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupAppbar()
    }

    private fun setupAppbar() {
        toolbar.inflateMenu(R.menu.help_menu)
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
        setupHelpButton()
    }

    private fun setupHelpButton() {
        toolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.help) {
                showHelpDialog()
            }
            true
        }
    }

    private fun showHelpDialog() {
        MessageDialog(
            getString(R.string.settings_title),
            getString(R.string.help_settings_toolbar_text)
        ).show(requireFragmentManager(), "AboutDialog")
    }
}



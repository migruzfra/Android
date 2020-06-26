package es.iessaladillo.miguelangelruz.androrecetas.ui.profile.add

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.base.observeEvent
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.extensions.hideSoftKeyboard
import es.iessaladillo.miguelangelruz.androrecetas.ui.avatars
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.add_profile_fragment.*

class AddProfileFragment : Fragment(R.layout.add_profile_fragment) {

    private lateinit var addProfileAdapter: AddProfileFragmentAdapter

    private val viewModel: AddProfileViewModel by viewModels {
        AddProfileViewModelFactory(
            AndroRecetasDatabase.getInstance(this.requireContext()).userDao,
            requireActivity().application
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        showPlayers(avatars)
        fabSaveUser.setOnClickListener { validateNewUserData() }
        observeEvents()


    }

    private fun observeEvents() {
        viewModel.message.observeEvent(this) {
            Snackbar.make(lstAvatars, it, Snackbar.LENGTH_LONG).show()
        }
        viewModel.onBack.observeEvent(this) {
            if (it) {
                requireActivity().onBackPressed()
            }
        }
        viewModel.currentPlayerId.observe(viewLifecycleOwner) {
            if (it == 0L) {
                imgCurrentUser.setImageResource(R.drawable.logo)
            } else {
                imgCurrentUser.setImageResource(it.toInt())
            }
        }
    }

    private fun validateNewUserData() {
        if (lblUserName.text.toString().isNotEmpty() && lblUserPassword.text.toString().length >= 4 && viewModel.currentPlayerId.value != 0L) {
            save()
            lblUserName.hideSoftKeyboard()
            Snackbar.make(requireView(), R.string.snackbar_message_user_created_successfully, Snackbar.LENGTH_LONG).show()
        } else {
            requestFocusOnLbl()
            viewModel.errorValidationMessage()
        }
    }

    private fun requestFocusOnLbl() {
        if (lblUserName.text.isBlank()) {
            lblUserName.requestFocus()
        } else if (lblUserPassword.text.length < 4) {
            lblUserPassword.requestFocus()
        }
    }

    private fun save() {
        viewModel.addUser(
            lblUserName.text.toString(),
            lblUserPassword.text.toString(),
            viewModel.currentPlayerId.value!!.toInt()
        )
    }

    private fun showPlayers(avatars: List<Int>) {
        addProfileAdapter.submitList(avatars)

    }

    private fun setupRecyclerView() {
        lstAvatars.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 4)
            adapter = addProfileAdapter

        }
    }

    private fun setupAdapter() {
        addProfileAdapter = AddProfileFragmentAdapter()
            .also {
                it.onItemClickListener = { position ->
                    selectAvatar(position)
                    addProfileAdapter.currentPosition = position
                    addProfileAdapter.notifyDataSetChanged()
                }
            }
    }

    private fun selectAvatar(position: Int) {
        viewModel.setCurrentPlayerId(avatars[position].toLong())
    }

    private fun setupToolbar() {
        toolbar.inflateMenu(R.menu.help_menu)
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_addprofile_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }
}
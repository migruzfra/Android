package es.iessaladillo.miguelangelruz.androrecetas.ui.profile.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.base.observeEvent
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.extensions.hideSoftKeyboard
import es.iessaladillo.miguelangelruz.androrecetas.ui.avatars
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.add_profile_fragment.*

class EditProfileFragment : Fragment(R.layout.add_profile_fragment) {

    private lateinit var playerEditFragment: EditProfileFragmentAdapter
    private lateinit var shoppinglist: String
    private val viewModel: EditProfileViewModel by viewModels {
        EditProfileViewModelFactory(
            AndroRecetasDatabase.getInstance(this.requireContext()).userDao,
            requireActivity().application
        )
    }
    private val userId: Long by lazy {
        requireArguments().getLong(getString(R.string.ARGS_USER_ID))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        viewModel.setCurrentPlayerAvatar(avatars.indexOf(viewModel.queryUser(userId).imageResId))
    }

    private fun setupViews() {
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        showAvatars(avatars)
        setupUserInfo()
        fabSaveUser.setOnClickListener { updateUser() }
        observeEvents()
    }

    private fun setupUserInfo() {
        viewModel.queryUser(userId).run {
            imgCurrentUser.setImageResource(this.imageResId)
            lblUserName.setText(this.nameUser)
            lblUserPassword.setText(this.password)
            shoppinglist = this.shoppingList
        }
    }

    private fun observeEvents() {
        viewModel.message.observeEvent(this) {
            Snackbar.make(lstAvatars, it, Snackbar.LENGTH_SHORT).show()
        }
        viewModel.onBack.observeEvent(this) {
            if (it) {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun updateUser() {
        viewModel.updateUser(
            userId,
            lblUserName.text.toString(),
            lblUserPassword.text.toString(),
            avatars[viewModel.currentPlayerAvatar.value!!],
            shoppinglist
        )
        Snackbar.make(requireView(), R.string.snackbar_message_user_edited_successfully, Snackbar.LENGTH_LONG).show()
        lblUserName.hideSoftKeyboard()
    }

    private fun showAvatars(avatars: List<Int>) {
        playerEditFragment.submitList(avatars)
    }

    private fun setupRecyclerView() {
        lstAvatars.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 4)
            adapter = playerEditFragment

        }
    }

    private fun setupAdapter() {
        playerEditFragment = EditProfileFragmentAdapter()
            .also {
                it.onItemClickListener = { position -> selectAvatar(position)
                    playerEditFragment.currentPosition = position
                    playerEditFragment.notifyDataSetChanged()}
            }
    }

    private fun selectAvatar(position: Int) {
        viewModel.setCurrentPlayerAvatar(position)
        viewModel.currentPlayerAvatar.observe(viewLifecycleOwner) {
            imgCurrentUser.setImageResource(avatars[it])
        }
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_editprofile_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }


}
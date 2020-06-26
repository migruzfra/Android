package es.iessaladillo.miguelangelruz.androrecetas.ui.profile

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.User
import es.iessaladillo.miguelangelruz.androrecetas.extensions.invisibleUnless
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.profile_fragment.*


class ProfileFragment : Fragment(R.layout.profile_fragment) {

    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(activity)
    }
    private lateinit var userAdapter: ProfileFragmentAdapter

    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModelFactory(
            AndroRecetasDatabase.getInstance
                (this.requireContext()).userDao, requireActivity().application
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupButtons()
        setupToolbar()
        setupAdapter()
        setupRecyclerView()
        observeLiveData()
        setupCurrentUser()
    }

    private fun setupCurrentUser() {
        viewModel.currentUserId.observe(viewLifecycleOwner) {
            if (viewModel.currentUserId.value != -1L && settings.getLong("currentPlayer", -1) != -1L) {
                val user = viewModel.queryUser(it)
                lblUserName.text = user.nameUser
                imgCurrentUser.setImageResource(user.imageResId)
                btnEdit.visibility = View.VISIBLE
                btnShoppingList.visibility = View.VISIBLE
                btnLogout.visibility = View.VISIBLE
                userAdapter.currentPosition = user.idUser.toInt() - 2
            } else {
                lblUserName.text = getString(R.string.user_selection_no_user_selected)
                imgCurrentUser.setImageResource(R.drawable.logo)
                btnEdit.visibility = View.INVISIBLE
                btnShoppingList.visibility = View.INVISIBLE
                btnLogout.visibility = View.INVISIBLE
            }
        }
    }

    private fun observeLiveData() {
        viewModel.users.observe(viewLifecycleOwner) {
            showUsers(it)
        }
    }

    private fun showUsers(users: List<User>) {
        lstUsers.post {
            userAdapter.submitList(users)
            imgNoUsers.invisibleUnless(users.isEmpty())
            lblNoUsers.invisibleUnless(users.isEmpty())
        }
    }

    private fun setupButtons() {
        fabAddUser.setOnClickListener { navigateToAddPlayer() }
        imgNoUsers.setOnClickListener { navigateToAddPlayer() }
        lblNoUsers.setOnClickListener { navigateToAddPlayer() }
        btnEdit.setOnClickListener { navigateToEdit(viewModel.currentUserId.value!!) }
        btnLogout.setOnClickListener { logout() }
        btnShoppingList.setOnClickListener { navigateToShoppingList(viewModel.currentUserId.value!!) }
    }

    private fun logout() {
        settings.edit { putLong("currentPlayer", -1L) }
        viewModel.setCurrentUserId(-1L)
        Snackbar.make(requireView(), R.string.snackbar_message_logout, Snackbar.LENGTH_LONG).show()
    }

    private fun setupRecyclerView() {
        lstUsers.run {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 3)
            adapter = userAdapter
        }
    }

    private fun setupAdapter() {
        userAdapter = ProfileFragmentAdapter().also {
            it.onItemClickListener = { it ->
                if (userAdapter.currentList[it].idUser != settings.getLong("currentPlayer", -1)) {
                    requestPassword(it)
                }
            }
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_profile_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }

    private fun selectCurrentPlayer(position: Int) {
        settings.edit { putLong("currentPlayer", userAdapter.currentList[position].idUser) }
        viewModel.setCurrentUserId(userAdapter.currentList[position].idUser)
    }

    private fun requestPassword(position: Int) {
        val alert = AlertDialog.Builder(requireContext())
        val editText = EditText(requireContext())
        editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        alert.setMessage(R.string.login_message)
        alert.setTitle(R.string.login_title)
        alert.setView(editText)
        alert.setPositiveButton(R.string.login_title) { _,_ ->
            if (editText.text.toString() == viewModel.queryUser(userAdapter.currentList[position].idUser).password) {
                selectCurrentPlayer(position)
                userAdapter.currentPosition = position
                userAdapter.notifyDataSetChanged()
                Snackbar.make(requireView(), R.string.snackbar_message_correct_password, Snackbar.LENGTH_LONG).show()
                navigateToDashboard()
            } else {
                Snackbar.make(requireView(), R.string.snackbar_message_incorrect_password, Snackbar.LENGTH_LONG).show()
            }
        }
        alert.show()
    }

    private fun navigateToEdit(userId: Long) {
        findNavController().navigate(
            R.id.editProfileDestination, bundleOf(
                getString(R.string.ARGS_USER_ID) to userId
            )
        )
    }

    private fun navigateToShoppingList(userId: Long) {
        findNavController().navigate(
            R.id.shoppingListDestination, bundleOf(
                getString(R.string.ARGS_USER_ID) to userId
            )
        )
    }

    private fun navigateToAddPlayer() {
        findNavController().navigate(R.id.navigateToAddProfile)
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.navigateToDashboard)
    }

}
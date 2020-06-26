package es.iessaladillo.miguelangelruz.androrecetas.ui.profile.shoppingList

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.base.OnToolbarAvailableListener
import es.iessaladillo.miguelangelruz.androrecetas.data.database.AndroRecetasDatabase
import es.iessaladillo.miguelangelruz.androrecetas.extensions.hideSoftKeyboard
import es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message.MessageDialog
import kotlinx.android.synthetic.main.shopping_list_fragment.*
import kotlin.properties.Delegates

class ShoppingListFragment : Fragment(R.layout.shopping_list_fragment) {

    private val userId: Long by lazy {
        requireArguments().getLong(getString(R.string.ARGS_USER_ID))
    }
    private val listAdapter: ShoppingListFragmentAdapter =
        ShoppingListFragmentAdapter()
            .also {
                it.onItemClickListener = { position -> deleteItem(position) }
            }

    private val viewModel: ShoppingListViewModel by viewModels {
        ShoppingListViewModelFactory(
            AndroRecetasDatabase.getInstance(requireContext()).userDao,
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
        listAdapter.submitList(viewModel.listItems)
    }

    private fun setupButtons() {
        setupButtonAddItem()
        setupButtonClear()
    }

    private fun setupButtonClear() {
        btnClear.setOnClickListener {
            viewModel.clearList()
            viewModel.updateShoppingList(userId)
            setupRecyclerView()
        }
    }

    private fun setupButtonAddItem() {
        btnAddItem.setOnClickListener {
            if (txtItem.text.toString().isNotBlank()) {
                viewModel.addItem(txtItem.text.toString().replace(", ", " ").replace(",", " "))
                txtItem.hideSoftKeyboard()
                txtItem.setText("")
                viewModel.updateShoppingList(userId)
                setupRecyclerView()
            } else {
                Snackbar.make(requireView(), R.string.error_add_steps_message, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteItem(position: Int) {
        viewModel.deleteItem(position)
        viewModel.updateShoppingList(userId)
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
        MessageDialog(getString(R.string.help_title), getString(R.string.help_shopping_toolbar_text)).show(
            requireFragmentManager(),
            "AboutDialog"
        )
    }

}

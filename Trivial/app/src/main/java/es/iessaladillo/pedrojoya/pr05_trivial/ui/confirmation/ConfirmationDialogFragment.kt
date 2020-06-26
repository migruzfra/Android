import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.commit
import es.iessaladillo.pedrojoya.pr05_trivial.R
import es.iessaladillo.pedrojoya.pr05_trivial.ui.game.game_won.GameWonFragment
import es.iessaladillo.pedrojoya.pr05_trivial.ui.title.TitleFragment

class ConfirmationDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    //Se construye el diálogo de confirmación estableciendo título, mensaje, botones y funcionalidad de cada botón
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.main_confirmation))
            .setMessage(getString(R.string.main_quit_game))
            .setPositiveButton(getString(R.string.main_yes)) { _, _ ->
                navigateToTitle()
            }
            .setNegativeButton(getString(R.string.main_no)) { _, _ ->
                //No hace ná
            }
            .create()


    private fun navigateToTitle() {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fcContent, TitleFragment.newInstance())
        }
    }
}
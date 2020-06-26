package es.iessaladillo.miguelangelruz.androrecetas.ui.dialogs.message

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import es.iessaladillo.miguelangelruz.androrecetas.R

class MessageDialog (private val title: String, private val message: String) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(getString(R.string.ok)) { _, _ -> }
            .create()
}
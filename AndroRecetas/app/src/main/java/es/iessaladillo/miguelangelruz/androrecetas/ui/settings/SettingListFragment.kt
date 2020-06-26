package es.iessaladillo.miguelangelruz.androrecetas.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import es.iessaladillo.miguelangelruz.androrecetas.R

class SettingsListFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences,rootKey)
    }


}

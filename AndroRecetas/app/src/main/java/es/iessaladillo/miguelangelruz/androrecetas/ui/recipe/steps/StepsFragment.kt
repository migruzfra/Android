package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.steps

import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import es.iessaladillo.miguelangelruz.androrecetas.R
import kotlinx.android.synthetic.main.step_slide.*
import kotlinx.android.synthetic.main.steps_fragment.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates
import java.util.Timer
import kotlin.concurrent.schedule

class StepsFragment : Fragment(R.layout.steps_fragment), TextToSpeech.OnInitListener {

    var codeRecipe by Delegates.notNull<Long>()
    lateinit var listStepsText: List<String>
    private lateinit var stepsFragmentAdapter: StepsFragmentAdapter
    private var tts: TextToSpeech? = null
    val settings: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }
    var silent by Delegates.notNull<Boolean>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tts = TextToSpeech(activity, this)
        setupViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = StepsFragmentArgs.fromBundle(requireArguments())
        listStepsText = args.steps.asList()
        codeRecipe = args.codeRecipe
    }

    private fun setupViews() {
        setupAdapter()
        silent = settings.getBoolean("silentMode", false)
        if (!silent) {
            //Ejecuta esta función un tiempo después para que de tiempo a que la petición llegue
            Timer("SettingUp", false).schedule(500) {
                tts!!.speak(listStepsText[0], TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }

        setupPageChangeListener()
    }

    private fun setupTabs() {
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }

    private fun setupViewPager() {
        viewPager.run {
            adapter = stepsFragmentAdapter
        }
    }

    private fun setupAdapter() {
        stepsFragmentAdapter =
            StepsFragmentAdapter(ArrayList(listStepsText), requireContext()).also {
                it.onFinishClickListener = { navigateToEndRecipe() }
                it.onNextClickListener = { viewPager.currentItem += 1 }
                it.onPreviousClickListener = { viewPager.currentItem -= 1 }
                it.onRepeatClickListener = {
                    if (!silent) {
                        tts!!.speak(listStepsText[it], TextToSpeech.QUEUE_FLUSH, null, "")
                    }
                }
            }
        setupViewPager()
        setupTabs()
    }

    private fun navigateToEndRecipe() {
        val action = StepsFragmentDirections.navigateToEndRecipe(codeRecipe)
        findNavController().navigate(action)

    }

    private fun setupPageChangeListener() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (!silent) {
                    Timer("SettingUp", false).schedule(300) {
                        tts!!.speak(listStepsText[position], TextToSpeech.QUEUE_FLUSH, null, "")
                    }
                }
            }
        })
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.getDefault())
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}
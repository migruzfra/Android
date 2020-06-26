package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.steps

import android.content.Context
import android.graphics.Typeface
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.miguelangelruz.androrecetas.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.step_slide.*
import kotlinx.android.synthetic.main.step_slide.view.*

class StepsFragmentAdapter(val stepList: ArrayList<String>, val context: Context) :
    RecyclerView.Adapter<StepsFragmentAdapter.ViewHolder>() {

    var currentStep: Int = 0
    var onFinishClickListener: ((Int) -> Unit)? = null
    var onNextClickListener: ((Int) -> Unit)? = null
    var onPreviousClickListener: ((Int) -> Unit)? = null
    var onRepeatClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.step_slide, parent, false)
        return ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return stepList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step: String = stepList[position]
        currentStep = position
        holder.bind(step)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            btnFinish.setOnClickListener { onFinishClickListener?.invoke(adapterPosition) }
            btnNext.setOnClickListener { onNextClickListener?.invoke(adapterPosition) }
            btnPrevious.setOnClickListener { onPreviousClickListener?.invoke(adapterPosition) }
            recipe.setOnClickListener { onRepeatClickListener?.invoke(adapterPosition) }
        }

        fun bind(step: String) {
            lblNumStep.typeface = Typeface.createFromAsset(context.assets, "fonts/Andora Demo.ttf")
            lblNumStep.text = context.getString(R.string.num_step, (layoutPosition + 1).toString())
            lblStep.text = step
            containerView.btnPrevious.visibility = View.VISIBLE
            if (adapterPosition == 0) {
                containerView.btnPrevious.visibility = View.INVISIBLE
            } else if (adapterPosition == stepList.size - 1) {
                containerView.btnFinish.visibility = View.VISIBLE
                containerView.btnNext.visibility = View.INVISIBLE
            }
            if (stepList.size == 1) {
                containerView.btnFinish.visibility = View.VISIBLE
                containerView.btnNext.visibility = View.INVISIBLE
            }
        }

    }

}
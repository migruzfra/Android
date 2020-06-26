package es.iessaladillo.miguelangelruz.androrecetas.ui.recipe.add.addSteps

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.miguelangelruz.androrecetas.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.step_item.*
import kotlinx.android.synthetic.main.step_item.view.*
import kotlinx.android.synthetic.main.step_item.view.imgDelete

class AddStepsFragmentAdapter :
    ListAdapter<String, AddStepsFragmentAdapter.ViewHolder>(TaskDiffCallback) {

    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.step_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = currentList[position]
        holder.bind(step, position)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init {
            imgDelete.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
        }

        fun bind(step: String, position: Int) {
            step.run {
                containerView.lblNumStep.text =  String.format("%s.  ", (position + 1).toString())
                containerView.lblStep.text = this
            }
        }
    }

    object TaskDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return false
        }

    }

}
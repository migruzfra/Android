package es.iessaladillo.miguelangelruz.androrecetas.ui.search.ranking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.miguelangelruz.androrecetas.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.ranking_item.*
import kotlinx.android.synthetic.main.ranking_item.view.*

class RankingFragmentAdapter :
    ListAdapter<String, RankingFragmentAdapter.ViewHolder>(TaskDiffCallback) {

    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.ranking_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = currentList[position]
        holder.bind(recipe, position)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init {
            clRoot.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
        }

        fun bind(recipe: String, position: Int) {
            recipe.run {
                containerView.lblNumStep.text =  String.format("%s.  ", (position + 1).toString())
                containerView.lblStep.text = this
                if (position == 0) {
                    lblNumStep.setTextColor(containerView.resources.getColor(R.color.colorAccent))
                    lblStep.setTextColor(containerView.resources.getColor(R.color.colorAccent))
                } else if (position == 1) {
                    lblNumStep.setTextColor(containerView.resources.getColor(R.color.colorSilver))
                    lblStep.setTextColor(containerView.resources.getColor(R.color.colorSilver))
                } else if (position == 2) {
                    lblNumStep.setTextColor(containerView.resources.getColor(R.color.colorBronze))
                    lblStep.setTextColor(containerView.resources.getColor(R.color.colorBronze))
                }
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
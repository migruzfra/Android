package es.iessaladillo.miguelangelruz.androrecetas.ui.search.searchbycuisine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.data.local.entity.RecipeOption
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.recipe_card_option.*
import kotlinx.android.synthetic.main.recipe_card_option.view.*

class SearchByCuisineFragmentAdapter :
    ListAdapter<String, SearchByCuisineFragmentAdapter.ViewHolder>(TaskDiffCallback) {

    var onItemClicked: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.recipe_card_option, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipeTitle = currentList[position]
        holder.bind(recipeTitle)
    }


    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init {
            clRoot.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(recipe: String) {
            recipe.run {
                containerView.lblRecipeCardOption.text = this
            }
        }
    }

    object TaskDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}
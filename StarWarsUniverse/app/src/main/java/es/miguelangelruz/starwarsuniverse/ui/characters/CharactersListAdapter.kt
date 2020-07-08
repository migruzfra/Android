package es.miguelangelruz.starwarsuniverse.ui.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.miguelangelruz.starwarsuniverse.R
import es.miguelangelruz.starwarsuniverse.data.response.CharacterResponse
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.card_option.*
import kotlinx.android.synthetic.main.card_option.view.*


class CharactersListAdapter :
    ListAdapter<CharacterResponse, CharactersListAdapter.ViewHolder>(TaskDiffCallback) {

    var onItemClicked: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.card_option, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = currentList[position]
        holder.bind(character)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        init {
            clRoot.setOnClickListener {
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(character: CharacterResponse) {
            character.run {
                containerView.lbl_card_option.text = this.name
            }
        }
    }

    object TaskDiffCallback : DiffUtil.ItemCallback<CharacterResponse>() {
        override fun areItemsTheSame(oldItem: CharacterResponse, newItem: CharacterResponse): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: CharacterResponse, newItem: CharacterResponse): Boolean {
            return oldItem == newItem
        }
    }
}
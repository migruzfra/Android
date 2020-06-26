package es.iessaladillo.miguelangelruz.androrecetas.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_item.*

class ProfileFragmentAdapter :
    ListAdapter<User, ProfileFragmentAdapter.ViewHolder>(UserDiffCallback){

    var onItemClickListener: ((Int) -> Unit)? = null

    var currentPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.user_item, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user: User = currentList[position]
        holder.bind(user)
    }



    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }
        }

        fun bind(user: User) {
            user.run {
                lblUser.text = user.nameUser
                imgUser.setImageResource(user.imageResId)
                if (currentPosition == adapterPosition) {
                    viewCheckP.visibility = View.VISIBLE
                } else {
                    viewCheckP.visibility = View.INVISIBLE
                }
            }

        }
    }

    object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return  oldItem.idUser == newItem.idUser
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return  oldItem == newItem
        }

    }

}

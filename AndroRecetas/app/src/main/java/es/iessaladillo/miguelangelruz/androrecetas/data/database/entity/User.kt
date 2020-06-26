package es.iessaladillo.miguelangelruz.androrecetas.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val idUser: Long,
    val nameUser: String,
    val password: String,
    val imageResId: Int,
    val recipesRated: String,
    val shoppingList: String
)

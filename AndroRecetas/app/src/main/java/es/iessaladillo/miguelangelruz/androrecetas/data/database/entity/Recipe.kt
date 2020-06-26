package es.iessaladillo.miguelangelruz.androrecetas.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val codeRecipe: Long,
    val nameRecipe: String,
    val descripRecipe: String,
    val group: String,
    val idUser: Long,
    val cuisine: String,
    val totalPoints: Float,
    val totalVoters: Int,
    val ingredients: String
)


package es.iessaladillo.miguelangelruz.androrecetas.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["codeRecipe"],
            childColumns = ["codeRecipe"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Step(
    @PrimaryKey(autoGenerate = true)
    val codeStep: Long,
    val descripStep: String,
    val codeRecipe: Long
)
package es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo

import androidx.room.*
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Recipe
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Step

data class RecipeWithSteps(
    @Embedded
    val recipe: Recipe,
    @Relation(
        parentColumn = "codeRecipe",
        entityColumn = "codeRecipe"
    )
    val steps: List<Step>
)
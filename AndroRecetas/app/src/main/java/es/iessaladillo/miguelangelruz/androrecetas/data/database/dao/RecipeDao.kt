package es.iessaladillo.miguelangelruz.androrecetas.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Recipe
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Step
import es.iessaladillo.miguelangelruz.androrecetas.data.database.pojo.RecipeWithSteps

@Dao
interface RecipeDao {

    @Insert
    fun insertRecipe(recipe: Recipe) : Long
    @Insert
    fun insertSteps(steps: List<Step>)

    @Update
    fun updateRatingRecipe(recipe: Recipe)
    @Query("UPDATE Recipe SET totalPoints = :totalPoints, totalVoters = :totalVoters WHERE codeRecipe = :codeRecipe")
    fun updateRatingRecipe(totalPoints: Float, totalVoters: Int, codeRecipe: Long)
    @Query("UPDATE Recipe SET ingredients = :ingredients WHERE codeRecipe = :codeRecipe")
    fun insertIngredients(ingredients: String, codeRecipe: Long)

    @Query("DELETE FROM Recipe WHERE codeRecipe = :codeRecipe")
    fun deleteRecipe(codeRecipe: Long)
    @Query("DELETE FROM Recipe WHERE codeRecipe NOT IN (SELECT DISTINCT codeRecipe FROM Step)")
    fun deleteRecipesWithoutSteps()
    @Query("DELETE FROM Recipe WHERE totalPoints/totalVoters < 1 AND totalVoters > 5")
    fun deleteJunkRecipes()

    @Transaction
    @Query("SELECT * FROM Recipe WHERE codeRecipe = :codeRecipe")
    fun queryRecipe(codeRecipe: Long): RecipeWithSteps

    @Transaction
    @Query("SELECT * FROM Recipe")
    fun queryAllRecipesWithSteps(): List<RecipeWithSteps>

    @Transaction
    @Query("SELECT * FROM Recipe WHERE totalVoters > 0 ORDER BY totalPoints/totalVoters DESC, totalVoters DESC LIMIT 5")
    fun queryBestRecipes(): List<RecipeWithSteps>

    @Transaction
    @Query("SELECT * FROM Recipe WHERE nameRecipe LIKE :nameRecipe")
    fun queryRecipeByNamee(nameRecipe: String): List<RecipeWithSteps>

    @Query("SELECT COUNT(*) FROM Recipe")
    fun queryNumRecipes(): Int

    @Query("SELECT codeRecipe FROM Recipe")
    fun queryListIdRecipes(): List<Long>

}
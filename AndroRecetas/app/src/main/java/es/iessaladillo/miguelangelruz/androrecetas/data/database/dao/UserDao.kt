package es.iessaladillo.miguelangelruz.androrecetas.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.User

@Dao
interface UserDao {
    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)
    @Query("UPDATE User SET recipesRated = :recipesRated WHERE idUser = :idUser")
    fun updateRecipesRated(recipesRated: String, idUser: Long)
    @Query("UPDATE User SET shoppingList = :shoppingList WHERE idUser = :idUser")
    fun updateShoppingList(shoppingList: String, idUser: Long)

    //Excluimos el 1 porque es el usuario que utilizaremos para añadir recetas de ejemplo.
    @Query("SELECT * FROM User WHERE idUser != 1")
    fun queryAllUsers(): LiveData<List<User>>

    @Query("SELECT nameUser FROM User WHERE idUser = :idUser")
    fun queryUsernameFromId(idUser: Long): String

    @Query("SELECT imageResId FROM User WHERE idUser = :idUser")
    fun queryUserImgFromId(idUser: Long): Int

    @Query("SELECT imageResId FROM User WHERE idUser = :idUser")
    fun getUserImgFromId(idUser: Long): Int

    @Query("SELECT * FROM User WHERE idUser = :idUser")
    fun queryUserFromId(idUser: Long): User

    @Query("SELECT shoppingList FROM User WHERE idUser = :idUser")
    fun queryShoppingList(idUser: Long): String
}
package es.iessaladillo.miguelangelruz.androrecetas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import es.iessaladillo.miguelangelruz.androrecetas.R
import es.iessaladillo.miguelangelruz.androrecetas.data.database.Converters
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.RecipeDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.dao.UserDao
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Recipe
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.Step
import es.iessaladillo.miguelangelruz.androrecetas.data.database.entity.User
import es.iessaladillo.miguelangelruz.androrecetas.data.local.enum.Group
import kotlin.concurrent.thread

@Database(
    entities = [Recipe::class, Step::class, User::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)

abstract class AndroRecetasDatabase : RoomDatabase() {
    abstract val recipeDao: RecipeDao
    abstract val userDao: UserDao

    companion object {

        @Volatile
        private var INSTANCE: AndroRecetasDatabase? = null

        fun getInstance(context: Context): AndroRecetasDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AndroRecetasDatabase::class.java,
                            "androrecetas_database"
                        ).addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                thread {
                                    INSTANCE!!.recipeDao.insertRecipe(
                                        Recipe(
                                            0,
                                            "Tortilla de patatas",
                                            "Uno de los platos más conocidos y emblemáticos de la cocina española",
                                            "Egg",
                                            1,
                                            "Mediterranean",
                                            4f,
                                            1,
                                            "6 huevos, 3 patatas grandes, 1/2 cebolla, 3 ajos pequeños, perejil fresco, sal, aceite"
                                        )
                                    )
                                    INSTANCE!!.recipeDao.insertSteps(
                                        listOf(
                                            Step(0, "Preparamos una sartén o cazuela con una buena cantidad de aceite. Troceamos las patatas y la cebolla. Dejamos que se fría durante 20 minutos a fuego lento, la patata tiene que pocharse.", 1),
                                            Step(0, "Batimos los huevos. En el mortero machacamos los ajos junto al perejil y lo añadimos junto a los huevos, batimos un poco más.", 1),
                                            Step(0, "Cuando la patata esté lista la vamos añadiendo al bol, escurriendo la mayor cantidad de aceite posible. Mezclamos todo bien.", 1),
                                            Step(0, "Por último pondremos un poco de aceite en la sartén (unas dos cucharadas soperas), cuando esté caliente añadimos la patata y dejamos que se haga por cada lado aproximadamente un minuto a fuego medio. Con ayuda de un plato le daremos la vuelta y ¡listo!.", 1)
                                        )
                                    )
                                    INSTANCE!!.userDao.insertUser(
                                        User(
                                            0,
                                            "Example",
                                            "",
                                            R.drawable.logo,
                                            "",
                                            ""
                                        )
                                    )
                                }


                            }
                        }).allowMainThreadQueries().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}


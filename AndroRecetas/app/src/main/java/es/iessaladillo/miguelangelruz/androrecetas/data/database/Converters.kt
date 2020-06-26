package es.iessaladillo.miguelangelruz.androrecetas.data.database

import androidx.room.TypeConverter

//Para que Room pueda manejar la lista de Long que tiene la entidad User
class Converters {

    @TypeConverter
    fun fromList(nums : MutableList<Long>) : String {
        var string = ""
        for (num in nums) {
            string += (num.toString() + ",")
        }
        return string
    }

    @TypeConverter
    fun toList(concatenatedNums: String) : MutableList<Long> {
        if (concatenatedNums.isNotBlank()) {
            return concatenatedNums.split(",").map { s -> s.toLong() }.toMutableList()
        } else {
            return mutableListOf()
        }
    }
}
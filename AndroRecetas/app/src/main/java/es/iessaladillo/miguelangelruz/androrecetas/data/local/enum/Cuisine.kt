package es.iessaladillo.miguelangelruz.androrecetas.data.local.enum

import es.iessaladillo.miguelangelruz.androrecetas.R

enum class Cuisine(val cuisine: String, val cuisineResId: Int) {
    ARABIAN("Arabian", R.string.arabian),
    ASIAN("Asian", R.string.asian),
    EUROPEAN("European", R.string.european),
    LATINAMERICAN("Latin American", R.string.latinamerican),
    MEDITERRANEAN("Mediterranean", R.string.mediterranean),
    OTHER("Other", R.string.other);
}
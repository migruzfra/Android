package es.iessaladillo.miguelangelruz.androrecetas.data.local.enum

import es.iessaladillo.miguelangelruz.androrecetas.R

enum class Group(val group: String, val groupResId: Int, val groupDescrResId: Int) {
    BREAD("Bread", R.string.group_title_bread, R.string.group_description_bread),
    COCKTAIL("Cocktail", R.string.group_title_cocktail, R.string.group_description_cocktail),
    DESSERT("Dessert", R.string.group_title_dessert, R.string.group_description_dessert),
    EGG("Egg", R.string.group_title_egg, R.string.group_description_egg),
    FISH("Fish", R.string.group_title_fish, R.string.group_description_fish),
    LEGUMES("Legumes", R.string.group_title_legumes, R.string.group_description_legumes),
    MEAT("Meat", R.string.group_title_meat, R.string.group_description_meat),
    MUSHROOMS("Mushrooms", R.string.group_title_mushrooms, R.string.group_description_mushrooms),
    PASTAPIZZA("Pasta/Pizza", R.string.group_title_pastapizza, R.string.group_description_pastapizza),
    RICECEREAL("Rice/Cereal", R.string.group_title_ricecereal, R.string.group_description_ricecereal),
    SAUCE("Sauce", R.string.group_title_sauce, R.string.group_description_sauce),
    SHELLFISH("Shellfish", R.string.group_title_shellfish, R.string.group_description_shellfish),
    SNACK("Snack", R.string.group_title_snack, R.string.group_description_snack),
    SOUP("Soup", R.string.group_title_soup, R.string.group_description_soup),
    VEGETABLES("Vegetables", R.string.group_title_vegetables, R.string.group_description_vegetables);
}
package com.logo.data.model.headline

data class SearchPlaceList(
    val list: ArrayList<SearchPlace>,
)

data class SearchPlace(
    val tile: String,
    var isSelected: Boolean = false
)
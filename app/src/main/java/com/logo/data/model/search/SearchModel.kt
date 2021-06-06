package com.logo.data.model.search

import android.annotation.SuppressLint
import com.logo.data.model.headline.SearchPlaceList
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
data class SearchModel(
    val query: String,
    val place: SearchPlaceList?,
    val dateFrom: Date?,
    val dateTo: Date?,
    val sortBy: SortQuery
) {
    val placeText: String
        get() {
            if (place == null) {
                return ""
            }
            return place.list.fold("", { acc, next ->
                if (!next.isSelected) {
                    acc
                } else {
                    if (acc.isEmpty()) {
                        "$acc ${next.tile}"
                    } else {
                        "$acc,${next.tile}"
                    }
                }
            })
        }

    val dateFromText: String
        get() {
            if (dateFrom == null) {
                return ""
            }
            return serverFormatter.format(dateFrom)
        }

    val dateToText: String
        get() {
            if (dateTo == null) {
                return ""
            }
            return serverFormatter.format(dateTo)
        }

    private val serverFormatter by lazy {
        SimpleDateFormat(serverFormat)
    }

    private val serverFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
}
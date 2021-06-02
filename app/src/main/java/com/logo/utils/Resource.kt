package com.logo.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val code: Int = 0) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?, code: Int, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, message, code)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
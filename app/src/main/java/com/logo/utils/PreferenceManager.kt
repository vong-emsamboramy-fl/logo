package com.logo.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class PreferencesManager private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SharePreferenceKey.PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private var currentInstance: PreferencesManager? = null

        @Synchronized
        fun instantiate(context: Context): PreferencesManager {
            currentInstance?.let { return it }
            currentInstance = PreferencesManager(context)
            return currentInstance!!
        }

        val shared: PreferencesManager
            get() {
                currentInstance?.let { return it }
                throw IllegalStateException("${PreferencesManager::class.java.simpleName} is not initialized, please instantiate(...) method first!")
            }
    }

    fun store(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun commit(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun <T> store(key: String, value: T) {
        val jsonString = Gson().toJson(value)
        store(key, jsonString)
    }

    fun <T> commit(key: String, value: T) {
        val jsonString = Gson().toJson(value)
        commit(key, jsonString)
    }

    fun <T> get(key: String, classType: Class<T>): T? {
        val jsonString = get(key) ?: ""
        return try {
            Gson().fromJson(jsonString, classType)
        } catch (ex: Exception) {
            null
        }
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
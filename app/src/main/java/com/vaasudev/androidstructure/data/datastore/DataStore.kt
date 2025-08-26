package com.vaasudev.androidstructure.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "localData")

class DataStore(val context: Context) {

    object PrefKey {
        const val USER_ID = "user_id"
        const val IS_SIGN_IN = "sign_in"
        const val API_KEY = "api_key"
        const val TOKEN = "token"
    }

    val isSignIn = booleanPreferencesKey(PrefKey.IS_SIGN_IN)
    val userId = stringPreferencesKey(PrefKey.USER_ID)
    val apiKey = stringPreferencesKey(PrefKey.API_KEY)
    val token = stringPreferencesKey(PrefKey.TOKEN)

    suspend fun setStringData(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun setIntData(key: Preferences.Key<Int>, value: Int) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun setBooleanData(key: Preferences.Key<Boolean>, value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun setLongData(key: Preferences.Key<Long>, value: Long) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    fun getStringData(key: Preferences.Key<String>): Flow<String> {
        return context.dataStore.data.map {
            it[key] ?: ""
        }
    }

    fun getIntData(key: Preferences.Key<Int>): Flow<Int> {
        return context.dataStore.data.map {
            it[key] ?: -1
        }
    }

    fun getBooleanData(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    fun getLongData(key: Preferences.Key<Long>): Flow<Long> {
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: 0L
        }
    }

    suspend fun clearDataStore() {
        /*val lan: String = getStringData(appLanguage).first()
        val image: String = getStringData(imageURL).first()
        val onboarding: Boolean = getBooleanData(isDoneOnboarding).first()*/

        context.dataStore.edit { preferences ->
            preferences.clear()
        }

       /* setStringData(appLanguage, lan)
        setStringData(imageURL, image)
        setBooleanData(isDoneOnboarding, onboarding)*/
    }
}
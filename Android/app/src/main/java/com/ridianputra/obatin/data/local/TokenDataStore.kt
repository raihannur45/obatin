package com.ridianputra.obatin.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ridianputra.obatin.data.UserProfileData
import com.ridianputra.obatin.data.network.response.LoginData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenDataStore private constructor(private val dataStore: DataStore<Preferences>) {

    fun loadToken(): Flow<LoginData> = dataStore.data.map { pref ->
        LoginData(
            pref[ACCESS_KEY] ?: "",
            pref[REFRESH_KEY] ?: ""
        )
    }

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        dataStore.edit { pref ->
            pref[ACCESS_KEY] = accessToken
            pref[REFRESH_KEY] = refreshToken
        }
    }

    fun loadDataUser(): Flow<UserProfileData> = dataStore.data.map { pref ->
        UserProfileData(
            pref[FULLNAME_KEY] ?: "",
            pref[EMAIL_KEY] ?: "",
            pref[USERNAME_KEY] ?: ""
        )
    }

    suspend fun saveDataUser(fullname: String, username: String, email: String) {
        dataStore.edit { pref ->
            pref[FULLNAME_KEY] = fullname
            pref[USERNAME_KEY] = username
            pref[EMAIL_KEY] = email
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it.remove(ACCESS_KEY)
            it.remove(REFRESH_KEY)
            it.remove(FULLNAME_KEY)
            it.remove(USERNAME_KEY)
            it.remove(EMAIL_KEY)
        }
    }

    fun loadWelcomeKey(): Flow<String> = dataStore.data.map { pref ->
        pref[WELCOME_KEY] ?: "0"
    }

    suspend fun saveWelcomeKey(state: String) {
        dataStore.edit { pref ->
            pref[WELCOME_KEY] = state
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: TokenDataStore? = null
        private val ACCESS_KEY = stringPreferencesKey("access_key")
        private val REFRESH_KEY = stringPreferencesKey("refresh_key")
        private val FULLNAME_KEY = stringPreferencesKey("fullname_key")
        private val USERNAME_KEY = stringPreferencesKey("username_key")
        private val EMAIL_KEY = stringPreferencesKey("email_key")
        private val WELCOME_KEY = stringPreferencesKey("welcome_key")

        fun getInstance(dataStore: DataStore<Preferences>) = INSTANCE ?: synchronized(this) {
            val instance = TokenDataStore(dataStore)
            INSTANCE = instance
            instance
        }
    }
}
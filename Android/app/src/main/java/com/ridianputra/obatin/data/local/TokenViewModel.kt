package com.ridianputra.obatin.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ridianputra.obatin.data.UserProfileData
import com.ridianputra.obatin.data.network.response.LoginData
import kotlinx.coroutines.launch

class TokenViewModel(private val dataStore: TokenDataStore) : ViewModel() {

    fun loadToken(): LiveData<LoginData> = dataStore.loadToken().asLiveData()

    fun saveToken(accessToken: String, refreshToken: String) {
        viewModelScope.launch { dataStore.saveToken(accessToken, refreshToken) }
    }

    fun loadDataUser(): LiveData<UserProfileData> = dataStore.loadDataUser().asLiveData()

    fun saveDataUser(fullname: String, username: String, email: String) {
        viewModelScope.launch { dataStore.saveDataUser(fullname, username, email) }
    }

    fun logout() {
        viewModelScope.launch { dataStore.logout() }
    }

    fun loadWelcomeKey(): LiveData<String> = dataStore.loadWelcomeKey().asLiveData()

    fun saveWelcomeKey(state: String) {
        viewModelScope.launch { dataStore.saveWelcomeKey(state) }
    }
}
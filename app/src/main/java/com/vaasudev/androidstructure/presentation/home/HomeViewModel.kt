package com.vaasudev.androidstructure.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaasudev.androidstructure.domain.utility.Result
import com.vaasudev.androidstructure.data.datastore.DataStore
import com.vaasudev.androidstructure.data.remote.ApiObject
import com.vaasudev.androidstructure.domain.repository.AuthRepository
import com.vaasudev.androidstructure.domain.utility.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStore: DataStore
) : ViewModel() {

    private val _uiState: MutableLiveData<Status> =
        MutableLiveData()
    val uiState: LiveData<Status> = _uiState

    fun callInit(version: String, deviceType: String) {
        viewModelScope.launch {
            _uiState.value = Status.Loading
            var url =""
            url = "${ApiObject.EndPoint.INIT}/$version/$deviceType"
            runBlocking { dataStore.getStringData(dataStore.userId).first().let {
                url = "${ApiObject.EndPoint.INIT}/$version/$deviceType/$it"
            } }
            repository.init(url = url).collect { response ->
                when (response) {
                    is Result.Error -> {
                        _uiState.value = Status.Error(response.error)
                    }
                    is Result.Success -> {
                        _uiState.value = Status.Success(response.data)
                    }
                }
            }
        }
    }

}
  
package dog.snow.androidrecruittest.ui.splash

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dog.snow.androidrecruittest.ui.shared.Event
import dog.snow.androidrecruittest.repository.CacheUpdateResult
import dog.snow.androidrecruittest.repository.Repository
import kotlinx.coroutines.*
import java.util.*

class SplashViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {


    private val TAG = "SplashViewModel"

    private val _navigateToListFragment = MutableLiveData<Event<Boolean>>()
    val navigateToListFragment: LiveData<Event<Boolean>>
        get() = _navigateToListFragment

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String>
        get() = _showError


    fun navigateToList() {
        _navigateToListFragment.value =
            Event(true)
    }

    fun tryAgain() {
        clearErrorMessage()
        updateCache()
    }

    fun updateCache() {

        Log.d(TAG, "updateCache: updating Cache")

        viewModelScope.launch {

            val result = repository.updateDataFromApiWithRetry()

            when (result) {
                is CacheUpdateResult.Error -> {
                    if (lastUpdateInLessThanTenMinutes()) {
                        navigateToList()
                    } else {
                        showError(result.exception)
                    }
                }
                is CacheUpdateResult.Success -> {

                    navigateToList()
                }
            }
        }
    }

    private fun lastUpdateInLessThanTenMinutes(): Boolean {

        val currentTime = Calendar.getInstance().timeInMillis
        val lastUpdateTime = repository.getLastUpdateDate()
        Log.d(TAG, "lastUpdateInLessThanTenMinutes: if ${lastUpdateTime + TEN_MINUTES > currentTime}")
        return lastUpdateTime + TEN_MINUTES > currentTime
    }

    private fun showError(exception: Exception) {
        _showError.value = exception.localizedMessage
    }

    private fun clearErrorMessage() {
        _showError.value = null
    }
}

const val TEN_MINUTES = 600000
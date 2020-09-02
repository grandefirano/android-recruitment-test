package dog.snow.androidrecruittest.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dog.snow.androidrecruittest.Event
import dog.snow.androidrecruittest.repository.CacheUpdateResult
import dog.snow.androidrecruittest.repository.Repository
import kotlinx.coroutines.*

class SplashViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _navigateToListFragment = MutableLiveData<Event<Boolean>>()
    val navigateToListFragment: LiveData<Event<Boolean>>
        get() = _navigateToListFragment

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String>
        get() = _showError


    fun navigateToList() {
        _navigateToListFragment.value = Event(true)
    }

    fun tryAgain() {
        clearErrorMessage()
        updateCache()
    }

    fun updateCache() {

        viewModelScope.launch {

            val result = repository.updateDataFromApi()

            when (result) {
                is CacheUpdateResult.Error -> {
                    if(lastUpdateInLessThanTenMinutes()){
                        navigateToList()
                    }else {
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

       return true
    }

    private fun showError(exception: Exception) {
        _showError.value = exception.localizedMessage
    }

    private fun clearErrorMessage() {
        _showError.value = null
    }
}
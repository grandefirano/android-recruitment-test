package dog.snow.androidrecruittest.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dog.snow.androidrecruittest.Event
import dog.snow.androidrecruittest.repository.CacheResult
import dog.snow.androidrecruittest.repository.Repository
import kotlinx.coroutines.*

class SplashViewModel @ViewModelInject constructor(
    private val repository: Repository
):ViewModel() {


    private val _navigateToListFragment = MutableLiveData<Event<Boolean>>()
    val navigateToListFragment: LiveData<Event<Boolean>>
        get() = _navigateToListFragment

    private val _showError = MutableLiveData<String>()
    val showError: LiveData<String>
        get() = _showError


    fun navigateToList(){
        _navigateToListFragment.value= Event(true)
    }

    fun updateCache(){
        viewModelScope.launch {

            val result=repository.updateDataFromApi()

            when(result){
                is CacheResult.Error->{
                    showError(result.exception)
                }
                is CacheResult.Success->{
                    withContext(Dispatchers.Main){
                        navigateToList()
                    }
                }
            }

        }
    }

    private fun showError(exception: Exception) {
        _showError.value=exception.localizedMessage
    }
}
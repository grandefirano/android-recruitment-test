package dog.snow.androidrecruittest.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dog.snow.androidrecruittest.Event
import dog.snow.androidrecruittest.repository.Repository
import kotlinx.coroutines.*

class SplashViewModel @ViewModelInject constructor(
    private val repository: Repository
):ViewModel() {


    private val _navigateToListFragment = MutableLiveData<Event<Boolean>>()
    val navigateToListFragment: LiveData<Event<Boolean>>
        get() = _navigateToListFragment


    fun navigateToList(){
        _navigateToListFragment.value= Event(true)
    }

    fun updateCache(){
        viewModelScope.launch {

            repository.updateDataFromApi()

            withContext(Dispatchers.Main){
            navigateToList()
        }
        }
    }
}
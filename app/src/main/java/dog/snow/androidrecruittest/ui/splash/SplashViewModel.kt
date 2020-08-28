package dog.snow.androidrecruittest.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        GlobalScope.launch {
        delay(5000)
            withContext(Dispatchers.Main){
            navigateToList()
        }
        }
    }
}
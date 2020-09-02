package dog.snow.androidrecruittest.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.repository.Repository
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val details: LiveData<Detail>
        get() = _details
    private val _details: MutableLiveData<Detail> = MutableLiveData()

    fun setDetailsFromDatabase(id: Int) {
        viewModelScope.launch {
            val detail = repository.getDetailsFromDatabase(id)
            _details.postValue(detail)
        }
    }

}
package dog.snow.androidrecruittest.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel @ViewModelInject constructor(
    private val repository: Repository
):ViewModel() {

    val searchQuery:LiveData<String>
    get() = _searchQuery
    private val _searchQuery: MutableLiveData<String> = MutableLiveData("")

    val listOfResults:LiveData<List<ListItem>>
    get() = _listOfResults
    private val _listOfResults:MutableLiveData<List<ListItem>> = MutableLiveData()


    fun updateFilteredListFromDatabase(searchQuery:String=""){

        viewModelScope.launch {

            val userPhoto=repository.getUserPhotosFromDatabase()
            withContext(Dispatchers.Default) {
                val listItem = userPhoto.map {
                    ListItem(
                        id = it.photoId,
                        title =it.photoTitle,
                        albumTitle =it.albumTitle,
                        thumbnailUrl =it.thumbnailUrl
                    )
                }
                withContext(Dispatchers.Main){
                    _listOfResults.value=listItem
                }
            }


        }

    }

    fun onItemClicked(id: Int) {
        //TODO: NAVIGATION

    }
}
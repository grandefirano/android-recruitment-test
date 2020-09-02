package dog.snow.androidrecruittest.ui.list

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dog.snow.androidrecruittest.ui.shared.Event
import dog.snow.androidrecruittest.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {


    private val _navigateToDetailsFragment = MutableLiveData<Event<Pair<Int, View>>>()
    val navigateToDetailsFragment: LiveData<Event<Pair<Int, View>>>
        get() = _navigateToDetailsFragment

    val searchQuery: MutableLiveData<String> = MutableLiveData("")

    val listOfResults: LiveData<List<ListItem>>
        get() = _listOfResults
    private val _listOfResults: MutableLiveData<List<ListItem>> by lazy {
        MutableLiveData<List<ListItem>>()
    }

    init {
        updateFilteredListFromDatabase()
    }

    private fun updateFilteredListFromDatabase(searchQuery: String = "") {

        viewModelScope.launch {

            val listItems = repository.getListItemsFromDatabase(searchQuery)
            withContext(Dispatchers.Main) {
                _listOfResults.value = listItems
            }
        }
    }

    fun onItemClicked(id: Int, view: View) {

        _navigateToDetailsFragment.value =
            Event(Pair(id, view))
    }

    fun filterList(query: String) {
        updateFilteredListFromDatabase(query)
    }
}
package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.ui.details.Detail
import dog.snow.androidrecruittest.ui.list.ListItem

interface Repository {

    suspend fun updateDataFromApi():CacheUpdateResult
    suspend fun getListItemsFromDatabase(searchQuery:String):List<ListItem>
    suspend fun getDetailsFromDatabase(id:Int):Detail
}
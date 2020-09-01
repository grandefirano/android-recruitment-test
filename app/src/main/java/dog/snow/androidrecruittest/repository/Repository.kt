package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.db.DatabaseUserPhoto
import dog.snow.androidrecruittest.ui.details.Detail
import dog.snow.androidrecruittest.ui.list.ListItem

interface Repository {

    suspend fun updateDataFromApi():CacheResult
    suspend fun getListItemsFromDatabase(searchQuery:String):List<ListItem>
    suspend fun getDetailsFromDatabase(id:Int):Detail
}
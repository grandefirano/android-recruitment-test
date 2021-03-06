package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.ui.details.Detail
import dog.snow.androidrecruittest.ui.list.ListItem

interface Repository {

    suspend fun updateDataFromApiWithRetry():CacheUpdateResult
    suspend fun getListItemsFromDatabase(searchQuery:String):List<ListItem>
    suspend fun getDetailsFromDatabase(id:Int):Detail
    fun setLastUpdateDate(timeStamp:Long)
    fun getLastUpdateDate():Long
}
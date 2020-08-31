package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.db.DatabaseUserPhoto

interface Repository {

    suspend fun updateDataFromApi():Boolean
    suspend fun getUserPhotosFromDatabase():List<DatabaseUserPhoto>
}
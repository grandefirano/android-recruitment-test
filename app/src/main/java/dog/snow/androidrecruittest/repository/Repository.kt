package dog.snow.androidrecruittest.repository

interface Repository {

    suspend fun updateDataFromApi():Boolean
}
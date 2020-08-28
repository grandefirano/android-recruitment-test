package dog.snow.androidrecruittest.repository

import javax.inject.Inject


class RepositoryImpl @Inject constructor() : Repository {
    override suspend fun updateDataFromApi(): Boolean {


        return true
    }


    suspend fun downloadPhotos(){

    }
    suspend fun downloadUsers(){

    }
    suspend fun downloadAlbums(){

    }
}
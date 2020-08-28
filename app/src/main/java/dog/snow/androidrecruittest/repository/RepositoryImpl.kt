package dog.snow.androidrecruittest.repository

class RepositoryImpl : Repository {
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
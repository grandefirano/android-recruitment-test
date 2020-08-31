package dog.snow.androidrecruittest.repository

import dog.snow.androidrecruittest.db.LocalDatabase
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val albumService:AlbumService,
    private val photoService: PhotoService,
    private val userService: UserService,
    private val database: LocalDatabase
) : Repository {
    override suspend fun updateDataFromApi(): Boolean {

        val albums=downloadAlbums()
        val users=downloadUsers()
        val photos=downloadPhotos()


        return true
    }


    suspend fun downloadPhotos(){

    }
    suspend fun downloadUsers(){

    }
    suspend fun downloadAlbums(){

    }
}
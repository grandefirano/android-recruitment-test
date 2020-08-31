package dog.snow.androidrecruittest.repository

import android.util.Log
import dog.snow.androidrecruittest.db.DatabaseUserPhoto
import dog.snow.androidrecruittest.db.LocalDatabase
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val albumService:AlbumService,
    private val photoService: PhotoService,
    private val userService: UserService,
    database: LocalDatabase
) : Repository {

    private val TAG = "RepositoryImpl"
    private val userPhotoDao=database.userPhotoDao()

    override suspend fun updateDataFromApi(): Boolean {


        val photos=downloadPhotos()
        Log.d(TAG, "updateDataFromApi: after photos ")
        val userPhotos=photos.map{ photo->
            Log.d(TAG, "updateDataFromApi: after photo ${photo.id}")
            val album=downloadAlbum(photo.albumId)
            Log.d(TAG, "updateDataFromApi: after album ${photo.albumId}")
            val user=downloadUser(album.userId)
            Log.d(TAG, "updateDataFromApi: after user ${album.userId} ")

            DatabaseUserPhoto(
                photoId =photo.id,
                photoTitle = photo.title,
                albumTitle = album.title,
                thumbnailUrl = photo.thumbnailUrl,
                email = user.email,
                phone = user.phone,
                url = photo.url,
                username = user.username
            )
        }


        userPhotoDao.updateListOfUsers(userPhotos)
        //change for check
        return true
    }

    override suspend fun getUserPhotosFromDatabase(searchQuery: String): List<DatabaseUserPhoto> {
        return withContext(Dispatchers.IO) {
            userPhotoDao.getUserPhotos(searchQuery.formatToDBFormatQuery())
        }
    }



    private suspend fun downloadPhotos():List<RawPhoto>{
       return photoService.getRawPhotos()
    }
    private suspend fun downloadUser(id:Int):RawUser{
        return userService.getRawUser(id)
    }
    private suspend fun downloadAlbum(id:Int):RawAlbum{
        return albumService.getRawAlbum(id)
    }


    fun String.formatToDBFormatQuery():String{
        return "%${this.replace(' ', '%')}%"
    }
}
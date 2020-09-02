package dog.snow.androidrecruittest.repository

import android.util.Log
import dog.snow.androidrecruittest.db.DatabaseUserPhoto
import dog.snow.androidrecruittest.db.LocalDatabase
import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import dog.snow.androidrecruittest.repository.model.RawUser
import dog.snow.androidrecruittest.repository.provider.PreferenceProvider
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import dog.snow.androidrecruittest.ui.details.Detail
import dog.snow.androidrecruittest.ui.list.ListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val albumService:AlbumService,
    private val photoService: PhotoService,
    private val userService: UserService,
    database: LocalDatabase,
    private val preferenceProvider: PreferenceProvider
) : Repository {

    private val TAG = "RepositoryImpl"
    private val userPhotoDao=database.userPhotoDao()

    override suspend fun updateDataFromApi(): CacheUpdateResult {
        return withContext(Dispatchers.IO) {
            try {
                val photos = downloadPhotos()
                val databaseUserPhotos = photos.map { photo ->

                    val album = downloadAlbum(photo.albumId)
                    val user = downloadUser(album.userId)

                    createDatabaseUserPhoto(photo, user, album)
                }

                userPhotoDao.updateListOfUsers(databaseUserPhotos)
                Log.d(TAG, "updateDataFromApi: lastTime ${Calendar.getInstance().timeInMillis}")
                setLastUpdateDate(Calendar.getInstance().timeInMillis)

                CacheUpdateResult.Success
            } catch (e: Exception) {
                Log.e(TAG, "updateDataFromApi: exception", e)
                CacheUpdateResult.Error(e)
            }
        }
    }

    override suspend fun getListItemsFromDatabase(searchQuery: String): List<ListItem> {
        return withContext(Dispatchers.IO) {
           val userPhoto= userPhotoDao.getUserPhotos(searchQuery.formatToDBFormatQuery())
             userPhoto.mapToListItems()
        }
    }

    override suspend fun getDetailsFromDatabase(id: Int):Detail {
        return withContext(Dispatchers.IO) {
            val userPhoto= userPhotoDao.getUserPhotoById(id)
           userPhoto.toDetail()
        }
    }

    override fun setLastUpdateDate(timeStamp: Long) {
        preferenceProvider.setLastUpdate(timeStamp)
    }

    override fun getLastUpdateDate(): Long {
       return preferenceProvider.getLastUpdate()
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

    private suspend fun createDatabaseUserPhoto(
        photo:RawPhoto,
        user:RawUser,
        album: RawAlbum
    ):DatabaseUserPhoto{
        return withContext(Dispatchers.Default) {
            DatabaseUserPhoto(
                photoId = photo.id,
                photoTitle = photo.title,
                albumTitle = album.title,
                thumbnailUrl = photo.thumbnailUrl,
                email = user.email,
                phone = user.phone,
                url = photo.url,
                username = user.username
            )
        }
    }
    private suspend fun DatabaseUserPhoto.toDetail():Detail{
        return withContext(Dispatchers.Default) {
            this@toDetail.run {
                Detail(photoId = photoId,
                    photoTitle = photoTitle,
                    albumTitle = albumTitle,
                    username = username,
                    email = email,
                    phone = phone,
                    url = url)
            }
        }
    }

    private suspend fun List<DatabaseUserPhoto>.mapToListItems():List<ListItem> {
        return withContext(Dispatchers.Default) {
            this@mapToListItems.map {
                ListItem(
                    id = it.photoId,
                    title = it.photoTitle,
                    albumTitle = it.albumTitle,
                    thumbnailUrl = it.thumbnailUrl
                )
            }
        }
    }

    fun String.formatToDBFormatQuery():String{
        return "%${this.replace(' ', '%')}%"
    }
}


sealed class CacheUpdateResult{
    object Success:CacheUpdateResult()
    data class Error(val exception:Exception):CacheUpdateResult()
}

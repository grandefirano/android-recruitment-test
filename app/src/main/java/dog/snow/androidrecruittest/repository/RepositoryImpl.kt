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
import dog.snow.androidrecruittest.ui.details.Detail
import dog.snow.androidrecruittest.ui.list.ListItem
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

        val userPhotos=photos.map{ photo->

            val album=downloadAlbum(photo.albumId)

            val user=downloadUser(album.userId)


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

    private suspend fun downloadPhotos():List<RawPhoto>{
       return photoService.getRawPhotos()
    }
    private suspend fun downloadUser(id:Int):RawUser{
        return userService.getRawUser(id)
    }
    private suspend fun downloadAlbum(id:Int):RawAlbum{
        return albumService.getRawAlbum(id)
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
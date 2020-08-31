package dog.snow.androidrecruittest.db

import androidx.room.*

@Dao
interface UserPhotoDao {


    @Transaction
    suspend fun updateListOfUsers(userPhotos:List<DatabaseUserPhoto>){
        clearUserPhotos()
        insertListOfUserPhotos(userPhotos)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfUserPhotos(userPhotos:List<DatabaseUserPhoto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPhoto(userPhotos:DatabaseUserPhoto)

    @Query("SELECT * FROM userPhotos WHERE photoTitle LIKE :searchQuery OR albumTitle LIKE :searchQuery")
    fun getUserPhotos(
        searchQuery:String="%"
    ):List<DatabaseUserPhoto>

    @Query("DELETE FROM userPhotos")
    suspend fun clearUserPhotos()
}
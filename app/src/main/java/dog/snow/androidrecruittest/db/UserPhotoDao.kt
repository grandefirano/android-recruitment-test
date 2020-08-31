package dog.snow.androidrecruittest.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserPhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfUserPhotos(userPhotos:List<DatabaseUserPhoto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPhoto(userPhotos:DatabaseUserPhoto)

    @Query("SELECT * FROM userPhotos")
    fun getUserPhotos():List<DatabaseUserPhoto>

    @Query("DELETE FROM userPhotos")
    suspend fun clearUserPhotos()
}
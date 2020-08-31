package dog.snow.androidrecruittest.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DatabaseUserPhoto::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase :RoomDatabase(){
    abstract fun userPhotoDao():UserPhotoDao
}
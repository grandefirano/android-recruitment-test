package dog.snow.androidrecruittest.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "userPhotos")
data class DatabaseUserPhoto (
    @PrimaryKey(autoGenerate = false)
    val photoId: Int,
    val photoTitle: String,
    val albumTitle: String,
    val thumbnailUrl: String,
    val username: String,
    val email: String,
    val phone: String,
    val url: String
):Parcelable
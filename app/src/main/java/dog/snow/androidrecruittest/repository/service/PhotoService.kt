package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawPhoto
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {

    @GET("photos")
    suspend fun getRawPhotos(
        @Query("_limit") limit:Int=100
    ):List<RawPhoto>

}
package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawAlbum
import dog.snow.androidrecruittest.repository.model.RawPhoto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface AlbumService {
    @GET("albums/{id}")
    suspend fun getRawAlbums(
        @Path("id")id:Int
    ):List<RawAlbum>

}
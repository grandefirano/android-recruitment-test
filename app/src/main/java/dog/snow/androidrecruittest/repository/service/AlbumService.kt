package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawAlbum
import retrofit2.http.GET
import retrofit2.http.Path



interface AlbumService {
    @GET("albums/{id}")
    suspend fun getRawAlbum(
        @Path("id")id:Int
    ):RawAlbum

}
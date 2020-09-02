package dog.snow.androidrecruittest.repository.service

import dog.snow.androidrecruittest.repository.model.RawUser
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{id}")
    suspend fun getRawUser(
        @Path("id")id:Int
    ):RawUser
}
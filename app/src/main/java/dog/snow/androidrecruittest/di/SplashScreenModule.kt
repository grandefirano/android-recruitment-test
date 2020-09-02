package dog.snow.androidrecruittest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.android.components.ApplicationComponent
import dog.snow.androidrecruittest.repository.service.AlbumService
import dog.snow.androidrecruittest.repository.service.PhotoService
import dog.snow.androidrecruittest.repository.service.UserService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object SplashScreenModule{
    @Provides
    @Singleton
    fun providesAlbumService(retrofit: Retrofit): AlbumService {
        return retrofit.create(AlbumService::class.java)
    }

    @Provides
    @Singleton
    fun providesPhotoService(retrofit: Retrofit): PhotoService {
        return retrofit.create(PhotoService::class.java)
    }

    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

}
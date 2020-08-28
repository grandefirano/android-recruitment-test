package dog.snow.androidrecruittest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object SplashScreenModule{
    @Provides
    @ActivityRetainedScoped
    @AlbumService
    fun providesAlbumService(retrofit: Retrofit):AlbumService{
        return retrofit.create(AlbumService::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    @PhotoService
    fun providesPhotoService(retrofit: Retrofit):PhotoService{
        return retrofit.create(PhotoService::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    @UserService
    fun providesUserService(retrofit: Retrofit):UserService{
        return retrofit.create(UserService::class.java)
    }

}
package dog.snow.androidrecruittest.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class AlbumService

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class PhotoService

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserService



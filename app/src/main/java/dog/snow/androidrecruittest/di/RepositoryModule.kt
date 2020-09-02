package dog.snow.androidrecruittest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dog.snow.androidrecruittest.repository.Repository
import dog.snow.androidrecruittest.repository.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(repository: RepositoryImpl):Repository
}
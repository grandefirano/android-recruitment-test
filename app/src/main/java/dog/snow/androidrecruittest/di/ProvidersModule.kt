package dog.snow.androidrecruittest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dog.snow.androidrecruittest.repository.provider.PreferenceProvider

import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ProvidersModule {

    @Singleton
    @Provides
    fun providesPreferenceProvider(@ApplicationContext applicationContext: Context): PreferenceProvider {
        return PreferenceProvider(
            applicationContext
        )
    }
}

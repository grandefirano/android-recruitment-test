package dog.snow.androidrecruittest.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dog.snow.androidrecruittest.db.LocalDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule{

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext applicationContext: Context):LocalDatabase{
        return Room.databaseBuilder(applicationContext,
            LocalDatabase::class.java,
            "SnowDogRecruitmentApp.db")
            .build()
    }
}
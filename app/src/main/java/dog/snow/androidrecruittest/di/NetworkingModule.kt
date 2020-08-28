package dog.snow.androidrecruittest.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [NetworkingModule.Declarations::class])
@InstallIn(ApplicationComponent::class)
object NetworkingModule {


    @InstallIn(ApplicationComponent::class)
    @Module
    interface Declarations{
        @Singleton
        @Binds
        fun bindsCallAdapterFactory(callAdapterFactory: CoroutineCallAdapterFactory):CallAdapter.Factory
    }

    @Singleton
    @Provides
    fun providesRetrofit(converterFactory: Converter.Factory, callAdapterFactory: CallAdapter.Factory):Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .build()
    }
    @Singleton
    @Provides
    fun providesConverterFactory():Converter.Factory{
        return MoshiConverterFactory.create()
    }

}





package example.test.phong.offlineapparch.dagger

import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import example.test.phong.offlineapparch.App
import example.test.phong.offlineapparch.CryptocurrenciesViewModelFactory
import example.test.phong.offlineapparch.MainActivity
import example.test.phong.offlineapparch.Utils
import example.test.phong.offlineapparch.db.CryptocurrenciesDao
import example.test.phong.offlineapparch.db.Database
import example.test.phong.offlineapparch.remote.ApiInterface
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}

@Module
class AppModule {

//    companion object {
//        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                // Change the table name to the correct one
//                database.execSQL("ALTER TABLE cryptocurrency RENAME TO cryptocurrencies")
//            }
//        }
//    }

    @Provides
    @Singleton
    fun provideCryptocurrenciesDb(app: App): Database {
        return Room.databaseBuilder(app, Database::class.java, "cryptocurrencies_db")
//                .addMigrations(MIGRATION_1_2)
//                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun provideCryptocurrenciesDao(database: Database): CryptocurrenciesDao {
        return database.cryptocurrenciesDao()
    }

    @Provides
    @Singleton
    fun provideCryptocurrenciesViewModelFactory(factory: CryptocurrenciesViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun provideUtils(app: App): Utils = Utils(app)
}

@Module
class NetModule() {
    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, moshi: Moshi, baseUrl: String): Retrofit {
        return Retrofit.Builder().client(okHttpClient).baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(
            ApiInterface::class.java)
}
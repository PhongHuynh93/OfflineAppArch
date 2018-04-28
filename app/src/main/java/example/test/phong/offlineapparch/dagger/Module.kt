package example.test.phong.offlineapparch.dagger

import android.arch.persistence.room.Room
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import example.test.phong.offlineapparch.App
import example.test.phong.offlineapparch.MainActivity
import example.test.phong.offlineapparch.db.CryptocurrenciesDao
import example.test.phong.offlineapparch.db.Database
import javax.inject.Singleton


@Module
abstract class BuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}

@Module
class AppModule(val app: App) {
    @Provides
    @Singleton
    fun provideApp(): App = app

    @Provides
    @Singleton
    fun provideCryptocurrenciesDb(app: App): Database {
        return Room.databaseBuilder(app, Database::class.java, "cryptocurrencies_db").build()
    }

    @Provides
    @Singleton
    fun provideCryptocurrenciesDao(database: Database): CryptocurrenciesDao {
        return database.cryptocurrenciesDao()
    }
}
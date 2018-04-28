package example.test.phong.offlineapparch.dagger

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import example.test.phong.offlineapparch.App
import example.test.phong.offlineapparch.MainActivity
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
}
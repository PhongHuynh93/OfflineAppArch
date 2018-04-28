package example.test.phong.offlineapparch.dagger

import dagger.Component
import dagger.android.AndroidInjectionModule
import example.test.phong.offlineapparch.App
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, BuildersModule::class, AppModule::class))
interface AppComponent {
    fun inject(app: App)
}
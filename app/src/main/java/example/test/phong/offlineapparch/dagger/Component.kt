package example.test.phong.offlineapparch.dagger

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import example.test.phong.offlineapparch.App
import javax.inject.Singleton



@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, BuildersModule::class, AppModule::class))
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder
        fun build(): AppComponent
    }
    fun inject(app: App)
}
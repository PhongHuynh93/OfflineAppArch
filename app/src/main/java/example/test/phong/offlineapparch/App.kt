package example.test.phong.offlineapparch

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import example.test.phong.offlineapparch.dagger.AppModule
import example.test.phong.offlineapparch.dagger.DaggerAppComponent
import javax.inject.Inject

class App: Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}
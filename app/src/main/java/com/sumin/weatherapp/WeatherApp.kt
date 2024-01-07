package com.sumin.weatherapp

import android.app.Application
import android.content.Context
import android.util.Log
import com.sumin.weatherapp.di.ApplicationComponent
import com.sumin.weatherapp.di.DaggerApplicationComponent
import io.github.aakira.napier.Antilog
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.LogLevel
import io.github.aakira.napier.Napier
import timber.log.Timber
//нужно собирать проект перед добавленем applicationComponent = DaggerApplicationComponent
class WeatherApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)

        if (BuildConfig.DEBUG) {
           /* Timber.plant(object : Timber.DebugTree() {
                override fun log(
                    priority: Int, tag: String?, message: String, t: Throwable?
                ) {
                    super.log(priority, "weather_$tag", message, t)
                }
            })
*/
            // disable firebase crashlytics
            //FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(false)
            // init napier
            Napier.base(DebugAntilog())
        } else {
           // Timber.plant(CrashReportingTree())

            // enable firebase crashlytics
           // FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
            // init napier
            Napier.base(CrashlyticsAntilog(this))
        }
    }

    class CrashlyticsAntilog(private val context: Context) : Antilog() {

        override fun performLog(priority: LogLevel, tag: String?, throwable: Throwable?, message: String?) {
            // send only error log
            if (priority < LogLevel.ERROR) return
           // FirebaseCrashlytics.getInstance().log("$tag : $message")

            throwable?.let {
                when {
                    // e.g. http exception, add a customized your exception message
                    // it is KtorException -> {
                    //      FirebaseCrashlytics.getInstance().log(priority.ordinal, "HTTP Exception", it.response?.errorBody.toString())
                    // }
                }
               // FirebaseCrashlytics.getInstance().recordException(it)
            }
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }
        }
    }
}
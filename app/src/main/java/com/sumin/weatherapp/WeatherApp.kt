package com.sumin.weatherapp

import android.app.Application
import android.util.Log
import com.sumin.weatherapp.di.ApplicationComponent
import com.sumin.weatherapp.di.DaggerApplicationComponent
import timber.log.Timber
//нужно собирать проект перед добавленем applicationComponent = DaggerApplicationComponent
class WeatherApp : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                /**
                 * Override [log] to modify the tag and add a "global tag" prefix to it. You can rename the String "global_tag_" as you see fit.
                 */
                override fun log(
                    priority: Int, tag: String?, message: String, t: Throwable?
                ) {
                    super.log(priority, "weather_$tag", message, t)
                }
                /**
                 * Override [createStackElementTag] to include a add a "method name" to the tag.
                 */
                /*override fun createStackElementTag(element: StackTraceElement): String {
                    return String.format(
                        "%s:%s",
                        element.methodName,
                        super.createStackElementTag(element)
                    )
                }
                */
            })

        } else {
            Timber.plant(CrashReportingTree())
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
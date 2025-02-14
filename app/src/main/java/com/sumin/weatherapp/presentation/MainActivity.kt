package com.sumin.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.sumin.weatherapp.WeatherApp
import com.sumin.weatherapp.data.network.api.ApiFactory
import com.sumin.weatherapp.domain.usecase.ChangeFavouriteStateUseCase
import com.sumin.weatherapp.domain.usecase.SearchCityUseCase
import com.sumin.weatherapp.presentation.root.DefaultRootComponent
import com.sumin.weatherapp.presentation.root.RootContent
import com.sumin.weatherapp.presentation.ui.theme.WeatherAppTheme
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var rootComponentFactory: DefaultRootComponent.Factory

   /* @Inject
    lateinit var searchCityUseCase: SearchCityUseCase

    @Inject
    lateinit var changeFavouriteStateUseCase: ChangeFavouriteStateUseCase
*/

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as WeatherApp).applicationComponent.inject(this)

        super.onCreate(savedInstanceState)

     /*  for first load Favourites
      val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            searchCityUseCase("пон").forEach{
                changeFavouriteStateUseCase.addToFavourite(it)
            }
        }*/
        // Create the root component before starting Compose
        val root = rootComponentFactory.create(defaultComponentContext())

        setContent {
            RootContent(component = root )
        }
    }
}
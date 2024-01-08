package com.sumin.weatherapp.presentation.root

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.sumin.weatherapp.domain.entity.City
import com.sumin.weatherapp.presentation.details.DefaultDetailsComponent
import com.sumin.weatherapp.presentation.favourite.DefaultFavouriteComponent
import com.sumin.weatherapp.presentation.search.DefaultSearchComponent
import com.sumin.weatherapp.presentation.search.OpenReason

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.github.aakira.napier.Napier
import kotlinx.parcelize.Parcelize

private const val TAG="DefaultRootComponent"
class DefaultRootComponent @AssistedInject constructor(
    private val detailsComponentFactory: DefaultDetailsComponent.Factory,
    private val favouriteComponentFactory: DefaultFavouriteComponent.Factory,
    private val searchComponentFactory: DefaultSearchComponent.Factory,
    @Assisted("componentContext") componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = Config.Favourite,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config, componentContext: ComponentContext
    ): RootComponent.Child {

        Napier.d(tag = TAG) { "child config: ${config}" }

        return when (config) {
            is Config.Details -> {
                Napier.d(tag = TAG) { "Details config: ${config}" }
                val component = detailsComponentFactory.create(
                    city = config.city, onBackClicked = {
                        navigation.pop()
                    }, componentContext = componentContext
                )
                RootComponent.Child.Details(component)
            }

            Config.Favourite -> {
                Napier.d(tag = TAG) { "Favourite config: ${config}" }

                val component = favouriteComponentFactory.create(onCityItemClicked = {
                    navigation.push(Config.Details(it))
                }, onAddFavouriteClicked = {
                    navigation.push(Config.Search(OpenReason.AddToFavourite))
                }, onSearchClicked = {
                    navigation.push(Config.Search(OpenReason.RegularSearch))
                }, componentContext = componentContext
                )
                RootComponent.Child.Favourite(component)
            }

            is Config.Search -> {
                Napier.d(tag = TAG) { "Search config: ${config}" }
                val component =
                    searchComponentFactory.create(openReason = config.openReason, onBackClicked = {
                        navigation.pop()
                    }, onCitySavedToFavourite = {
                        navigation.pop()
                    }, onForecastForCityRequested = {
                        navigation.push(Config.Details(it))
                    }, componentContext = componentContext
                    )
                RootComponent.Child.Search(component)
            }
        }
    }


    sealed interface Config : Parcelable {

        @Parcelize
        data object Favourite : Config

        @Parcelize
        data class Search(val openReason: OpenReason) : Config

        @Parcelize
        data class Details(val city: City) : Config
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("componentContext") componentContext: ComponentContext
        ): DefaultRootComponent
    }
}
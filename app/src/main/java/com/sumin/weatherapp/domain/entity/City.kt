package com.sumin.weatherapp.domain.entity

import com.arkivanov.essenty.parcelable.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class City(
    val id: Int,
    val name: String,
    val country: String,
) :Parcelable

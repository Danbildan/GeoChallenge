package com.example.geochallenge.game


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "cities")
class CityTask(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int? = 0,

    @SerializedName("name_ru")
    @ColumnInfo(name = "city_name")
    var city: String? = "",

    @SerializedName("country_en")
    @Ignore
    var countryEN: String? = "",


    @ColumnInfo(name = "country")
    var country: String? = "",

    @SerializedName("country_ru")
    @ColumnInfo(name = "country_ru")
    var countryRU: String? = "",

    @ColumnInfo(name = "latitude") var latitude: Double? = 0.0,
    @ColumnInfo(name = "longitude") var longitude: Double? = 0.0,
    @ColumnInfo(name = "level") var level: Int? = 0,
    @ColumnInfo(name = "rating") var rating: Int? = 0
)
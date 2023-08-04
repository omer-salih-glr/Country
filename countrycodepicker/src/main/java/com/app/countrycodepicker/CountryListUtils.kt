/*
 * Created by Emirhan KOLVER on 9/26/22, 2:57 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 9/23/22, 10:07 AM
 */

package com.app.phoneCountryCodepicker

import android.content.Context
import android.widget.ImageView
import com.app.countrycodepicker.CountryListModel
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.io.BufferedReader
import java.util.*

object CountryListUtils {

    /**
     * Loads the country list from [R.raw.country_list]
     */
    fun getCountryList(context: Context): CountryListModel {
        val inputStream = context.resources.openRawResource(R.raw.country_list)
        val bufferedReader = BufferedReader(inputStream.reader())
        val sb = StringBuilder()
        bufferedReader.use { reader ->
            var line = reader.readLine()
            while (line != null) {
                sb.append(line + "\n")
                line = reader.readLine()
            }
        }
        return Gson().fromJson(sb.toString(), CountryListModel::class.java)
    }

    /**
     * Loads the country list from [R.raw.country_list]
     */
    fun getCurrentCountry(context: Context): CountryListModel.CountryListModelItem {
        val list = getCountryList(context)
        val country = Locale.getDefault().country
        list.forEach {
            if (it.code.contentEquals(country, true)) return it
        }
        return CountryListModel.CountryListModelItem(
            "US",
            "+1",
            "United States"
        )
    }

    /**
     * [phoneCountryCode]:+90, +49, +1...
     */
    fun findCountryData(
        context: Context,
        phoneCountryCode: String
    ): CountryListModel.CountryListModelItem? {
        return CountryListUtils.getCountryList(context).find { it.dialCode == phoneCountryCode }
    }

    /**
     * Returns the localized country name from [TR,US,FR,DE] like strings.
     */
    val String.localizedCountryName: String
        get() = Locale("", this).displayCountry


    /**
     * [phoneCountryCode]: Examples: [TR,US,FR,GE,DE] like country codes.
     *
     * [imageView]: The imageview that flag image to be loaded.
     */
    fun loadCountryImage(phoneCountryCode: String, imageView: ImageView) {
        Glide.with(imageView)
            .load("https://flagcdn.com/w160/${phoneCountryCode.lowercase()}.png")
            .into(imageView)
    }


}
/*
 * Created by Emirhan KOLVER on 9/26/22, 2:57 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 9/23/22, 10:07 AM
 */

package com.app.countrycodepicker


import com.google.gson.annotations.SerializedName

class CountryListModel : ArrayList<CountryListModel.CountryListModelItem>() {
    data class CountryListModelItem(
        /**
         * "code": "AL"
         */
        @SerializedName("code")
        val code: String,
        /**
         * "dial_code": "+355",
         */
        @SerializedName("dial_code")
        val dialCode: String,
        /**
         * "name": "Albania",
         */
        @SerializedName("name")
        val name: String
    )
}
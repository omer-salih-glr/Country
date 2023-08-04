/*
 * Created by Emirhan KOLVER on 9/26/22, 2:57 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 9/23/22, 2:23 PM
 */

package com.app.phoneCountryCodepicker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.app.phoneCountryCodepicker.databinding.ActivityCountryPickerBinding
import com.google.gson.Gson

class CountryPickerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryPickerBinding
    private lateinit var adapter: CountryPickerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryPickerBinding.inflate(layoutInflater)
        initUI()
        setContentView(binding.root)
    }

    private fun initUI() {
        adapter = CountryPickerAdapter(CountryListUtils.getCountryList(this)) {
            setResult(0, Intent().apply {
                putExtra("data", Gson().toJson(it))
            })
            finish()
        }
        binding.recyclerView.adapter = adapter
        binding.etSearch.addTextChangedListener {
            adapter.searchText(it.toString())
        }
    }

}
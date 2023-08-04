/*
 * Created by Emirhan KOLVER on 9/26/22, 2:57 PM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 9/23/22, 10:07 AM
 */

package com.app.phoneCountryCodepicker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.countrycodepicker.CountryListModel
import com.app.countrycodepicker.utils.ListDiffer
import com.app.phoneCountryCodepicker.CountryListUtils.localizedCountryName
import com.app.phoneCountryCodepicker.databinding.ListItemChooseCountryBinding

class CountryPickerAdapter(
    private val list: CountryListModel,
    private val onCountryPicked: (CountryListModel.CountryListModelItem) -> Unit
) : RecyclerView.Adapter<CountryPickerAdapter.VH>() {

    private var filteredList = ArrayList(list)

    inner class VH(
        private val binding: ListItemChooseCountryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun setData(data: CountryListModel.CountryListModelItem) {
            binding.countryName.text = data.code.localizedCountryName
            binding.root.setOnClickListener { onCountryPicked(data) }
            CountryListUtils.loadCountryImage(data.code, binding.ivCountyFlag)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val lai = LayoutInflater.from(parent.context)
        return VH(ListItemChooseCountryBinding.inflate(lai, parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.setData(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun searchText(searchStr: String) {
        val newList = list.filter {
            val countryName = it.code.localizedCountryName.lowercase()
            countryName.contains(searchStr.lowercase())
        }.toMutableList()
        ListDiffer(
            filteredList,
            newList,
            { oldData, newData -> oldData.code.contentEquals(newData.code, true) },
            { oldData, newData -> oldData.name.contentEquals(newData.name, true) }
        ).updateList(this)
    }

    companion object {
        private const val TAG = "CountryPickerAdapter"
    }

}
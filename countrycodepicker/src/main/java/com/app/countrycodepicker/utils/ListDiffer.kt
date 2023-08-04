/*
 * Created by Emirhan KOLVER on 9/27/22, 9:57 AM
 * Copyright (c) 2022 . All rights reserved.
 * Last modified 9/27/22, 9:54 AM
 */

package com.app.countrycodepicker.utils

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


/**
 * Example usage at RecyclerView.Adapter class.
 *
 *```
 * var list = mutableListOf<GetNumbersHistoryResponse.Data>()
 *     set(value) {
 *         ListDiffer(
 *             field,
 *             value,
 *             { oldData, newData -> oldData.offline.contentEquals(newData.offline) },
 *             { oldData, newData -> oldData.online.contentEquals(newData.online) }
 *         ).updateList(this)
 *     }
 *```
 */
class ListDiffer<T : Any?>(
    private val oldList: MutableList<T>,
    private val newList: MutableList<T>,
    /**
     * Called by the DiffUtil to decide whether two object represent the same Item.
     * <p>
     * For example, if your items have unique ids, this method should check their id equality.
     *
     * @param oldData The item in the old list
     * @param newData The item in the new list
     * @return True if the two items represent the same object or false if they are different.
     */
    private val areItemsTheSame: (oldData: T, newData: T) -> Boolean,
    /**
     * Called by the DiffUtil when it wants to check whether two items have the same data.
     * DiffUtil uses this information to detect if the contents of an item has changed.
     * <p>
     * DiffUtil uses this method to check equality instead of {@link Object#equals(Object)}
     * so that you can change its behavior depending on your UI.
     * For example, if you are using DiffUtil with a
     * {@link RecyclerView.Adapter RecyclerView.Adapter}, you should
     * return whether the items' visual representations are the same.
     * <p>
     * This method is called only if {@link #areItemsTheSame(int, int)} returns
     * {@code true} for these items.
     *
     * @param oldData The item in the old list
     * @param newItemPosition The  item in the new list which replaces the oldItem
     * @return True if the contents of the items are the same or false if they are different.
     */
    private val areContentsTheSame: (oldData: T, newData: T) -> Boolean,
) : DiffUtil.Callback() {

    private fun getOld(index: Int) = oldList[index]
    private fun getNew(index: Int) = newList[index]
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areItemsTheSame(getOld(oldItemPosition), getNew(newItemPosition))
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return areContentsTheSame(getOld(oldItemPosition), getNew(newItemPosition))
    }


    /**
     * It'll gonna clear the [oldList] and add all contents of [newList] to itself.
     *
     * There's no need to equalize anything to anything.
     */
    fun updateList(adapter: RecyclerView.Adapter<*>) = with(DiffUtil.calculateDiff(this)) {
        oldList.clear()
        oldList.addAll(newList)
        dispatchUpdatesTo(adapter)
    }
}
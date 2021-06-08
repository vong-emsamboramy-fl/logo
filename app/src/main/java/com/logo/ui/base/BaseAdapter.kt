package com.logo.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    protected var items: ArrayList<T> = arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun getItems(): List<T> {
        return items
    }

    open fun set(data: T) {
        this.items = ArrayList(arrayListOf(data))
        notifyDataSetChanged()
    }

    open fun set(data: List<T>) {
        this.items = ArrayList(data)
        notifyDataSetChanged()
    }

    open fun add(data: List<T>) {
        val startIndex = this.items.size
        this.items.addAll(data)
        notifyItemRangeInserted(startIndex, data.size)
    }

    open fun add(data: T) {
        val startIndex = this.items.size
        this.items.add(data)
        notifyItemRangeInserted(startIndex, 1)
    }

    open fun update(position: Int, data: T) {
        this.items[position] = data
        notifyItemChanged(position)
    }

    open fun remove(position: Int) {
        this.items.removeAt(position)
        notifyItemRemoved(position)
    }

    open fun remove(item: T) {
        val position = items.indexOf(item)
        remove(position)
    }

    fun removeFirst() {
        remove(items.first())
    }

    fun removeLast() {
        remove(items.last())
    }

    fun clear() {
        items = arrayListOf()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return onCreateItemHolder(layoutInflater, parent, viewType)
    }

    protected abstract fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder

    override fun getItemCount(): Int = items.size

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindItemHolder(holder, position)
    }

    protected abstract fun onBindItemHolder(holder: RecyclerView.ViewHolder, position: Int)
}
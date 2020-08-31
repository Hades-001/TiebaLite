package com.huanchengfly.tieba.post.adapters.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.huanchengfly.tieba.post.components.MyViewHolder

abstract class BaseAdapter<Item>(
        val context: Context,
        items: List<Item>? = null
) : RecyclerView.Adapter<MyViewHolder>() {
    private var itemList: MutableList<Item> = (items ?: emptyList()).toMutableList()

    var onItemClickListener: OnItemClickListener<Item>? = null
        private set

    var onItemLongClickListener: OnItemLongClickListener<Item>? = null
        private set

    fun setOnItemClickListener(listener: OnItemClickListener<Item>?) {
        onItemClickListener = listener
    }

    fun setOnItemClickListener(listener: ((viewHolder: MyViewHolder, item: Item, position: Int) -> Unit)?) {
        onItemClickListener = object : OnItemClickListener<Item> {
            override fun onClick(viewHolder: MyViewHolder, item: Item, position: Int) {
                if (listener != null) {
                    listener(viewHolder, item, position)
                }
            }
        }
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener<Item>?) {
        onItemLongClickListener = listener
    }

    fun setOnItemLongClickListener(listener: ((viewHolder: MyViewHolder, item: Item, position: Int) -> Boolean)?) {
        onItemLongClickListener = object : OnItemLongClickListener<Item> {
            override fun onLongClick(viewHolder: MyViewHolder, item: Item, position: Int): Boolean {
                if (listener != null) {
                    return listener(viewHolder, item, position)
                }
                return false
            }
        }
    }

    override fun getItemCount(): Int = getCount()

    fun getItem(position: Int): Item = itemList[position]

    fun getItemList(): MutableList<Item> = itemList

    fun getCount() = itemList.size

    fun setData(items: List<Item>?) {
        itemList.clear()
        itemList.addAll(items ?: emptyList())
        notifyDataSetChanged()
    }

    open fun remove(position: Int) {
        if (position < itemList.size && position >= 0) {
            itemList.removeAt(position)
            notifyItemRemoved(position)
            if (position != itemList.size) {
                this.notifyItemRangeChanged(position, itemList.size - position)
            }
        }
    }

    open fun insert(items: List<Item>, position: Int) {
        if (position <= itemList.size && position >= 0) {
            itemList.addAll(position, items)
            notifyItemRangeInserted(position, items.size)
            this.notifyItemRangeChanged(position, itemList.size - position)
        }
    }

    open fun insert(items: List<Item>) {
        insert(items, itemList.size)
    }

    open fun insert(data: Item, position: Int) {
        if (position <= itemList.size && position >= 0) {
            itemList.add(position, data)
            notifyItemInserted(position)
            this.notifyItemRangeChanged(position, itemList.size - position)
        }
    }

    open fun insert(data: Item) {
        this.insert(data, itemList.size)
    }

    open fun reset() {
        itemList.clear()
        notifyDataSetChanged()
    }
}
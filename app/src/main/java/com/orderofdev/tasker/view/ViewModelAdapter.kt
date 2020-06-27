package com.orderofdev.tasker.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class ViewModelAdapter(list: ObservableArrayList<Any>) :RecyclerView.Adapter<ViewModelAdapter.ViewHolder>(){
    data class CellInfo(val layoutId: Int, val bindingId: Int)

    var items = list

    init {
        this.notifyDataSetChanged()
        list.addOnListChangedCallback(object: ObservableList.OnListChangedCallback<ObservableArrayList<Any>>(){
            override fun onChanged(sender: ObservableArrayList<Any>?) {
                this@ViewModelAdapter.notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableArrayList<Any>?, positionStart: Int, itemCount: Int) {
                this@ViewModelAdapter.notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(sender: ObservableArrayList<Any>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                when{
                    itemCount == 1 -> this@ViewModelAdapter.notifyItemMoved(fromPosition, toPosition)
                    fromPosition < toPosition -> {//end
                        for(i in (0 until itemCount).reversed()){
                            this@ViewModelAdapter.notifyItemMoved(fromPosition + i, toPosition + i)
                        }
                    }
                    fromPosition > toPosition -> {//start
                        for(i in (0 until itemCount)){
                            this@ViewModelAdapter.notifyItemMoved(fromPosition + i, toPosition + i)
                        }
                    }
                }
            }

            override fun onItemRangeInserted(sender: ObservableArrayList<Any>?, positionStart: Int, itemCount: Int) {
                this@ViewModelAdapter.notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(sender: ObservableArrayList<Any>?, positionStart: Int, itemCount: Int) {
                this@ViewModelAdapter.notifyItemRangeChanged(positionStart, itemCount)
            }

        })
    }

    private val cellMap = Hashtable<Class<out Any>, CellInfo>()

    protected fun cell(clazz: Class<out Any>, @LayoutRes layoutId: Int, bindingId: Int) {
        cellMap[clazz] = CellInfo(layoutId, bindingId)
    }

    private fun getCellInfo(viewModel: Any): CellInfo {
        return cellMap[viewModel.javaClass] ?:
        throw Exception("Cell info for class ${viewModel.javaClass.name} not found.")
    }

    override fun getItemCount(): Int = items.size
    private val sharedObjects = Hashtable<Int, Any>()

    override fun getItemViewType(position: Int): Int {
        return getCellInfo(items[position]).layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        val viewHolder = ViewHolder(view)
        sharedObjects.forEach { viewHolder.binding.setVariable(it.key, it.value) }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cellInfo = getCellInfo(items[position])
        if (cellInfo.bindingId != 0)
            holder.binding.setVariable(cellInfo.bindingId, items[position])
    }

    protected fun sharedObject(sharedObject: Any, bindingId: Int) {
        sharedObjects[bindingId] = sharedObject
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: ViewDataBinding = DataBindingUtil.bind(view)!!
    }
}
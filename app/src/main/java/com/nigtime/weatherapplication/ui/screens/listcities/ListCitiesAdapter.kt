/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.ui.screens.listcities

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.SelectedCityData
import kotlinx.android.synthetic.main.item_city_list.view.*


class ListCitiesAdapter constructor(private val listener: Listener) :
    RecyclerView.Adapter<ListCitiesAdapter.ViewHolder>(),
    ItemTouchController.TouchAdapter<ListCitiesAdapter.ViewHolder> {

    interface Listener {
        fun onRequestDrag(viewHolder: RecyclerView.ViewHolder)
        fun onItemSwiped(
            item: SelectedCityData,
            position: Int,
            items: MutableList<SelectedCityData>
        )

        fun onItemsMoved(items: List<SelectedCityData>)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentItem: SelectedCityData? = null

        fun bindCity(city: SelectedCityData) {
            currentItem = city
            itemView.itemListCityName.text = city.name
            itemView.itemListCityStateAndCountry.text = getStateAndCounty(city)
        }

        fun getCurrentItem() = currentItem ?: error("current item == null")

        fun removeCurrentItem() {
            currentItem = null
        }

        private fun getStateAndCounty(city: SelectedCityData): CharSequence {
            return if (city.hasState()) {
                if (city.hasCountry()) {
                    "%s, %s".format(city.countryName, city.stateName)
                } else {
                    city.stateName
                }
            } else {
                city.countryName
            }
        }

        fun bindDragListener(onStartDrag: (ViewHolder) -> Unit) {
            itemView.itemListCityDragArea.setOnTouchListener { _, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        onStartDrag(this@ViewHolder)
                    }
                }
                true
            }
        }

        fun unbindDragListener() {
            itemView.itemListCityDragArea.setOnClickListener(null)
        }
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SelectedCityData>() {
            override fun areItemsTheSame(
                oldItem: SelectedCityData,
                newItem: SelectedCityData
            ): Boolean {
                return oldItem.cityId == newItem.cityId
            }

            override fun areContentsTheSame(
                oldItem: SelectedCityData,
                newItem: SelectedCityData
            ): Boolean {
                return oldItem.cityId == newItem.cityId && oldItem.listIndex == newItem.listIndex
            }
        }
    }


    //TODO remove it
    object DiffCallbackFactory {

        fun newDiffCallback(
            oldMap: List<SelectedCityData>,
            newMap: List<SelectedCityData>
        ): DiffUtil.Callback {
            return DiffCallback(oldMap, newMap)
        }

        private class DiffCallback constructor(
            private val old: List<SelectedCityData>,
            private val new: List<SelectedCityData>
        ) :
            DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = old[oldItemPosition]
                val new = new[newItemPosition]
                return old.cityId == new.cityId
            }

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val old = old[oldItemPosition]
                val new = new[newItemPosition]
                return old.cityId == new.cityId && old.listIndex == new.listIndex
            }
        }
    }

    /**
     * [AsyncListDiffer] для вычисления изменений в списке
     */
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    /**
     * Коллекция содержащая изменения в позициях и учитывающая удаляемые элементы
     */
    private var mutableItems = mutableListOf<SelectedCityData>()

    fun submitList(newList: List<SelectedCityData>) {
        differ.submitList(newList) {
            mutableItems = newList.toMutableList()
        }
    }

    fun insertItemToList(item: SelectedCityData, position: Int) {
        mutableItems.add(position, item)
        notifyItemInserted(position)
        //если между удалением и отменой удаления, происходило перемещение элементов
        //в списке, то возвращенный элемент не сохранится. Отправляем список ещё
        //раз что бы это исправить. Не проверяем наличие движений, для упрощения реализаций.
        listener.onItemsMoved(mutableItems)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bindDragListener(listener::onRequestDrag)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbindDragListener()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.removeCurrentItem()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_city_list, parent, false))
    }

    override fun getItemCount(): Int = mutableItems.size

    private fun getItem(position: Int): SelectedCityData = mutableItems[position]

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCity(getItem(position))
    }

    override fun canDropOver(current: ViewHolder, target: ViewHolder): Boolean {
        return true
    }

    override fun dispatchMove(moved: ViewHolder, target: ViewHolder): Boolean {
        //перезаписываем индексы
        mutableItems[target.adapterPosition] = moved.getCurrentItem()
        mutableItems[moved.adapterPosition] = target.getCurrentItem()
        //перемещаем элементы
        notifyItemMoved(moved.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onItemSwiped(swiped: ViewHolder) {
        //удаляем элемент из коллекции
        val swipedItem = swiped.getCurrentItem()
        val swipedPosition = swiped.adapterPosition
        mutableItems.removeAt(swipedPosition)
        //удаляем визуально
        notifyItemRemoved(swiped.adapterPosition)
        listener.onItemSwiped(swipedItem, swipedPosition, mutableItems)
    }

    override fun clearItemView(viewHolder: ViewHolder) {
        viewHolder.itemView.isSelected = false
    }

    override fun allMovementCompleted() {
        listener.onItemsMoved(mutableItems)
    }

    override fun onItemStartMove(viewHolder: ViewHolder) {
        viewHolder.itemView.isSelected = true
    }
}
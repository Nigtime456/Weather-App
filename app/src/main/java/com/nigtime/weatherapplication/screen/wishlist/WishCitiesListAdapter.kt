/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.screen.wishlist

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.common.utility.list.ItemTouchController
import com.nigtime.weatherapplication.domain.city.WishCity
import kotlinx.android.synthetic.main.item_wish_city.view.*

/**
 * TODO загрузка погоды + презентер для адаптера
 *
 * Как должна происходить загрузка данных:
 * К каждому ViewHolder презентер, который сохраняется в VM
 * получается так: ViewModelProvider.get(position,VM)
 *
 */

//TODO может BaseAdapter сюда прикрутить
class WishCitiesListAdapter constructor(private val listener: Listener) :
    RecyclerView.Adapter<WishCitiesListAdapter.ViewHolder>(),
    ItemTouchController.TouchAdapter<WishCitiesListAdapter.ViewHolder> {

    interface Listener {
        fun onRequestDrag(viewHolder: RecyclerView.ViewHolder)
        fun onItemSwiped(
            item: WishCity,
            position: Int,
            items: MutableList<WishCity>
        )

        fun onItemsMoved(items: List<WishCity>)
        fun onItemClick(position: Int)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var currentItem: WishCity? = null

        fun bindCity(city: WishCity) {
            currentItem = city
            itemView.itemWishName.text = city.name
            itemView.itemWishStateAndCountry.text = city.getStateAndCounty()
        }

        fun getCurrentItem() = currentItem ?: error("current item == null")

        fun recycle() {
            currentItem = null
        }

        fun attachClickListener(onClick: (Int) -> Unit) {
            itemView.setOnClickListener { onClick(adapterPosition) }
        }

        fun attachDragListener(onStartDrag: (ViewHolder) -> Unit) {
            itemView.itemWishDragArea.setOnTouchListener { _, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        onStartDrag(this@ViewHolder)
                    }
                }
                true
            }
        }

        fun removeListeners() {
            itemView.itemWishDragArea.setOnClickListener(null)
            itemView.setOnClickListener(null)
        }
    }

    private companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WishCity>() {
            override fun areItemsTheSame(
                oldItem: WishCity,
                newItem: WishCity
            ): Boolean {
                return oldItem.cityId == newItem.cityId
            }

            override fun areContentsTheSame(
                oldItem: WishCity,
                newItem: WishCity
            ): Boolean {
                return oldItem.cityId == newItem.cityId && oldItem.listIndex == newItem.listIndex
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
    private var mutableItems = mutableListOf<WishCity>()

    fun submitList(newList: List<WishCity>) {
        differ.submitList(newList) {
            mutableItems = newList.toMutableList()
        }
    }

    fun insertItemToList(item: WishCity, position: Int) {
        mutableItems.add(position, item)
        notifyItemInserted(position)
        //если между удалением и отменой удаления, происходило перемещение элементов
        //в списке, то возвращенный элемент не сохранится. Отправляем список ещё
        //раз что бы это исправить. Не проверяем наличие движений, для упрощения реализаций.
        listener.onItemsMoved(mutableItems)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.attachClickListener(listener::onItemClick)
        holder.attachDragListener(listener::onRequestDrag)
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.removeListeners()
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_wish_city, parent, false))
    }

    override fun getItemCount(): Int = mutableItems.size

    private fun getItem(position: Int): WishCity = mutableItems[position]

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
        //уведомляем слушатель
        listener.onItemSwiped(swipedItem, swipedPosition, mutableItems)
    }

    override fun clearItemView(viewHolder: ViewHolder) {
        viewHolder.itemView.isSelected = false
    }

    override fun allMovementCompleted() {
        listener.onItemsMoved(mutableItems)
    }

    override fun onStartMoveItem(viewHolder: ViewHolder) {
        viewHolder.itemView.isSelected = true
    }
}
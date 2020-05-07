/*
 * Сreated by Igor Pokrovsky. 2020/5/7
 */

/*
 * Сreated by Igor Pokrovsky. 2020/4/24
 */

package com.nigtime.weatherapplication.common.helper.list

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
class ItemTouchController<T : RecyclerView.ViewHolder>(
    private val touchAdapter: TouchAdapter<T>
) : ItemTouchHelper.Callback() {

    /**
     * Интерфейс адаттера
     */
    interface TouchAdapter<T : RecyclerView.ViewHolder> {
        /**
         * Вызывается когда уточняется, может ли элемент [target]
         * сдвинутся под [current]
         * @param current - элемент который двигается
         * @param target - элемент который должен сдвинутся
         * @return true - [target] - может сдвинутся
         */
        fun canDropOver(current: T, target: T): Boolean

        /**
         * Вызывается когда элемент хочет сдвинутся на другую позицию.
         * @return true - [moved] разрешено перетаскивать в позицию
         * [target]
         */
        fun dispatchMove(moved: T, target: T): Boolean

        /**
         * Вызывается когда элемент свайпнут
         * @param swiped - свайпнутый элемент
         */
        fun onItemSwiped(swiped: T)


        fun onItemStartMove(viewHolder: T)
        /**
         * Вызывается когда все перемещения завершены и
         * список прибывает в нормальном состоянии.
         */
        fun allMovementCompleted()

        /**
         * Вызывается когда элемент может быть очищена
         */
        fun clearItemView(viewHolder: T)
    }

    private var hasDrag = false

    override fun canDropOver(
        recyclerView: RecyclerView,
        current: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return touchAdapter.canDropOver(current as T, target as T)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            touchAdapter.onItemStartMove(viewHolder as T)
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE && hasDrag) {
            hasDrag = false
            touchAdapter.allMovementCompleted()
        }
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        touchAdapter.clearItemView(viewHolder as T)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.DOWN or ItemTouchHelper.UP
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun isLongPressDragEnabled(): Boolean = false


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        hasDrag = touchAdapter.dispatchMove(viewHolder as T, target as T)
        return hasDrag
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        touchAdapter.onItemSwiped(viewHolder as T)
    }
}
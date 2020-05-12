/*
 * Сreated by Igor Pokrovsky. 2020/5/12
 */

package com.nigtime.weatherapplication.screen.wishlist

import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.common.utility.list.ItemTouchController

class AdapterPresenter  <VH : RecyclerView.ViewHolder> : ItemTouchController.TouchAdapter<VH> {


    override fun canDropOver(current: VH, target: VH): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchMove(moved: VH, target: VH): Boolean {
        TODO("Not yet implemented")
    }

    override fun onItemSwiped(swiped: VH) {
        TODO("Not yet implemented")
    }

    override fun onStartMoveItem(viewHolder: VH) {
        TODO("Not yet implemented")
    }

    override fun allMovementCompleted() {
        TODO("Not yet implemented")
    }

    override fun clearItemView(viewHolder: VH) {
        TODO("Not yet implemented")
    }


}
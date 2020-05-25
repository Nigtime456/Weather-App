/*
 * Сreated by Igor Pokrovsky. 2020/5/23
 */

package com.gmail.nigtime456.weatherapplication.screen.notifications

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.gmail.nigtime456.weatherapplication.R
import com.gmail.nigtime456.weatherapplication.common.util.ThemeUtils
import com.gmail.nigtime456.weatherapplication.common.util.list.ColorDividerDecoration
import com.gmail.nigtime456.weatherapplication.domain.location.SavedLocation
import com.gmail.nigtime456.weatherapplication.screen.common.BaseFragment
import com.gmail.nigtime456.weatherapplication.screen.common.BasePresenterProvider
import com.gmail.nigtime456.weatherapplication.screen.common.NavigationController
import com.gmail.nigtime456.weatherapplication.screen.notifications.list.NotificationsAdapter
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment :
    BaseFragment<NotificationsView, NotificationsPresenter, NavigationController>(R.layout.fragment_notifications),
    NotificationsView {

    override fun getListenerClass(): Class<NavigationController>? = NavigationController::class.java

    override fun getPresenterProvider(): BasePresenterProvider<NotificationsPresenter> {
        return ViewModelProvider(this).get(NotificationsPresenterProvider::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initList()
    }

    private fun initList() {
        notificationsList.apply {
            itemAnimator = null
            addItemDecoration(getDivider())
            adapter = NotificationsAdapter()
        }
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeUtils.getAttrColor(requireContext(), R.attr.colorControlHighlight)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }

    override fun showProgressLayout() {
        //TODO
    }

    override fun submitList(locations: List<SavedLocation>) {
        (notificationsList.adapter as NotificationsAdapter).submitList(locations, true)
    }


}
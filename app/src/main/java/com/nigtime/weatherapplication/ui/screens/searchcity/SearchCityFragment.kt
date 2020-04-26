/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.searchcity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.nigtime.weatherapplication.R
import com.nigtime.weatherapplication.db.data.SearchCityData
import com.nigtime.weatherapplication.db.repository.SelectedCitySourceImpl
import com.nigtime.weatherapplication.db.service.AppDatabase
import com.nigtime.weatherapplication.ui.screens.common.BaseFragment
import com.nigtime.weatherapplication.ui.screens.common.ExtendLifecycle
import com.nigtime.weatherapplication.ui.screens.common.NavigationController
import com.nigtime.weatherapplication.ui.screens.searchcity.paging.PagingCityAdapter
import com.nigtime.weatherapplication.ui.screens.searchcity.paging.PagingListLoaderImpl
import com.nigtime.weatherapplication.ui.tools.ColorSpanHelper
import com.nigtime.weatherapplication.ui.tools.ThemeHelper
import com.nigtime.weatherapplication.ui.tools.ToastController
import com.nigtime.weatherapplication.ui.tools.list.ColorDividerDecoration
import com.nigtime.weatherapplication.ui.tools.list.LiftOnScrollListener
import com.nigtime.weatherapplication.utility.rx.MainSchedulerProvider
import kotlinx.android.synthetic.main.fragment_search_city.*

/**
 * TODO краши
 * Из за анимации краши.
 * Исправить анимацию: удалить анимацию ViewAnimator, написать свою crossfade.
 * Если не поможет поменять ViewAnimator на FrameLayout
 */
class SearchCityFragment :
    BaseFragment<SearchCityView, SearchCityPresenter, SearchCityFragment.Listener>(),
    SearchCityView {

    interface Listener : NavigationController

    private lateinit var toastController: ToastController
    private lateinit var liftOnScrollListener: LiftOnScrollListener

    override fun provideMvpPresenter(): SearchCityPresenter {
        val citiesDao = AppDatabase.Instance.get(requireContext()).geoCityDao()
        val selectedCitiesDao = AppDatabase.Instance.get(requireContext()).selectedCityDao()
        val pagingListLoader =
            PagingListLoaderImpl(citiesDao, MainSchedulerProvider.INSTANCE)
        val selectedCitiesSource = SelectedCitySourceImpl(citiesDao, selectedCitiesDao)
        return SearchCityPresenter(
            MainSchedulerProvider.INSTANCE,
            pagingListLoader,
            selectedCitiesSource
        )
    }

    override fun provideListenerClass(): Class<Listener>? = Listener::class.java


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_city, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        liftOnScrollListener.release()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this, lifecycleBus, ExtendLifecycle.DESTROY_VIEW)
        toastController = ToastController(requireContext())
        presenter.onViewCreated()
        initViews()
    }

    private fun initViews() {
        initRecycler()

        fragmentSearchCityEditText.simpleTextListener {
            presenter.processInput(it)
        }

        fragmentSearchCityCurrentLocation.setOnClickListener {
            toastController.showToast("TODO")
        }

        fragmentSearchCityToolbar.setNavigationOnClickListener {
            presenter.onClickToolbarNavigationButton()
        }
    }

    private fun initRecycler() {
        fragmentSearchCityRecycler.apply {
            adapter = PagingCityAdapter(getSpanHelper())
            addItemDecoration(getDivider())
            PagingCityAdapter.ItemClickClickListener(this) { searchCityData ->
                presenter.onClickListItem(searchCityData)
            }
            setLiftOnScrollAppBar(this)
        }
    }

    private fun setLiftOnScrollAppBar(recyclerView: RecyclerView) {
        liftOnScrollListener = LiftOnScrollListener(recyclerView, this::liftAppBar)
    }

    private fun liftAppBar(lift: Boolean) {
        fragmentSearchCityAppbar.isSelected = lift
    }

    private fun getSpanHelper(): ColorSpanHelper {
        val highlightColor = ThemeHelper.getColor(requireContext(), R.attr.colorAccent)
        return ColorSpanHelper(highlightColor)
    }

    private fun getDivider(): ColorDividerDecoration {
        val dividerColor = ThemeHelper.getColor(requireContext(), R.attr.themeDividerColor)
        val dividerSize = resources.getDimensionPixelSize(R.dimen.divider_size)
        return ColorDividerDecoration(
            dividerColor,
            dividerSize
        )
    }


    override fun submitList(pagedList: PagedList<SearchCityData>) {
        fragmentSearchCityRecycler.smoothScrollToPosition(0)
        (fragmentSearchCityRecycler.adapter as PagingCityAdapter).submitList(pagedList)
    }

    //TODO исправь этот спагети
    override fun showHint() {
        liftAppBar(false)
        fragmentSearchCityRecycler.visibility = View.INVISIBLE
        fragmentSearchCityProgressBar.hide()
        fragmentSearchCityNotFound.visibility = View.INVISIBLE
        fragmentSearchCityHint.visibility = View.VISIBLE
    }

    override fun showProgressBar() {
        liftAppBar(false)
        fragmentSearchCityRecycler.visibility = View.INVISIBLE
        fragmentSearchCityNotFound.visibility = View.INVISIBLE
        fragmentSearchCityHint.visibility = View.INVISIBLE
        fragmentSearchCityProgressBar.show()

    }

    override fun showList() {
        fragmentSearchCityNotFound.visibility = View.INVISIBLE
        fragmentSearchCityHint.visibility = View.INVISIBLE
        fragmentSearchCityProgressBar.hide()
        fragmentSearchCityRecycler.visibility = View.VISIBLE
    }

    override fun showMessageEmpty() {
        liftAppBar(false)
        fragmentSearchCityHint.visibility = View.INVISIBLE
        fragmentSearchCityProgressBar.hide()
        fragmentSearchCityRecycler.visibility = View.INVISIBLE
        fragmentSearchCityNotFound.visibility = View.VISIBLE

    }

    override fun showMessageAlreadySelected() {
        toastController.showToast(R.string.fragment_search_city_already_selected)
    }

    override fun navigateToPreviousScreen() {
        activity?.onBackPressed()
    }

    private fun EditText.simpleTextListener(block: (String) -> Unit) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                block(s.toString())
            }
        })
    }

}

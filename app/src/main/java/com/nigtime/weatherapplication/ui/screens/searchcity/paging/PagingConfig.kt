/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.searchcity.paging

import androidx.paging.PagedList

/**
 * Конфиг для подгружаемого спсика, управляет количеством подгружаемых элементов за раз.
 */
object PagingConfig {
    private const val PAGE_SIZE = 80
    private const val PRE_FETCH = 40
    private const val MAX_SIZE = PAGE_SIZE + PRE_FETCH * 2

    fun default() = PagedList.Config.Builder().run {
        setEnablePlaceholders(false)
        setPageSize(PAGE_SIZE)
        setPrefetchDistance(PRE_FETCH)
        setMaxSize(MAX_SIZE)
        build()
    }
}
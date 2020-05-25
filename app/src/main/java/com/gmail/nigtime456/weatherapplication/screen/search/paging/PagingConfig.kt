/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.gmail.nigtime456.weatherapplication.screen.search.paging

import androidx.paging.PagedList

/**
 * Конфиг для подгружаемого спсика, управляет количеством подгружаемых элементов за раз.
 */
class PagingConfig {
    companion object {
        private const val PAGE_SIZE = 80
        private const val PRE_FETCH = 40
        private const val MAX_SIZE = PAGE_SIZE + PRE_FETCH * 2
    }

    fun getDefault() = PagedList.Config.Builder().run {
        setEnablePlaceholders(false)
        setPageSize(PAGE_SIZE)
        setPrefetchDistance(PRE_FETCH)
        setMaxSize(MAX_SIZE)
        build()
    }
}
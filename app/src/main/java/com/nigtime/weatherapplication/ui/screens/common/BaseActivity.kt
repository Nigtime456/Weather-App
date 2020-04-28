/*
 * Сreated by Igor Pokrovsky. 2020/4/23 
 */

package com.nigtime.weatherapplication.ui.screens.common

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Базовый класс для создания активити с презентером.
 * Подклассы сами присоединяют презентер.
 *
 */

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseActivity : AppCompatActivity() {

    /**
     * Шина для уведомления презентера об событиях ЖЦ
     */
    protected val lifecycleBus: Subject<ExtendLifecycle> = PublishSubject.create()

    override fun onPause() {
        super.onPause()
        lifecycleBus.onNext(ExtendLifecycle.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleBus.onNext(ExtendLifecycle.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleBus.onNext(ExtendLifecycle.DESTROY)
    }
}
/*
 * Сreated by Igor Pokrovsky. 2020/4/29
 */

package com.nigtime.weatherapplication.common.testing

import android.content.Context
import com.nigtime.weatherapplication.domain.settings.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class FakeSettingsManager constructor(private val context: Context) :
    SettingsManager {

    private val subject: Subject<UnitFormatter> = BehaviorSubject.create()

    init {

        subject.onNext(
            UnitFormatter(
                context,
                UnitOfTemp.Celsius,
                UnitOfSpeed.KilometrePerHour,
                UnitOfPressure.MBar,
                UnitOfLength.Kilometre
            )
        )
    }

    override fun getUnitFormatter(): Observable<UnitFormatter> {
        return subject
    }
}
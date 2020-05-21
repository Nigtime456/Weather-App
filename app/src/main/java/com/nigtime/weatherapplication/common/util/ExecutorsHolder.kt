/*
 * Сreated by Igor Pokrovsky. 2020/5/21
 */

package com.nigtime.weatherapplication.common.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object ExecutorsHolder {
    private val maxIOThreads = Runtime.getRuntime().availableProcessors() * 2
    val dataBaseExecutor: Executor = Executors.newSingleThreadExecutor(DatabaseThreadFactory())
    val IOExecutor: Executor = Executors.newFixedThreadPool(maxIOThreads, IOThreadFactory())
    val singleExecutor: Executor = Executors.newSingleThreadExecutor(SingleThreadFactory())


    private class SingleThreadFactory : ThreadFactory {
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "Single-Thread")
        }
    }

    private class IOThreadFactory : ThreadFactory {
        private val number = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "IO-Thread-[${number.getAndIncrement()}]")
        }
    }

    private class DatabaseThreadFactory : ThreadFactory {
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "Sync-Database")
        }
    }
}
/*
 * Сreated by Igor Pokrovsky. 2020/4/23
 */

package com.gmail.nigtime456.weatherapplication.common.log

import android.util.Log

/**
 * Реализация логера.
 *
 * @param tag - тег
 * @param printThreadInfo - печатать информацию о потоке
 */
@Suppress("ConstantConditionIf")
class CustomLogger constructor(private val tag: String, private val printThreadInfo: Boolean) {

    companion object {
        private const val LOCATION_PATTERN = "%s.%s():%s"
        private const val THREAD_PATTERN = "%s:%s"
        private const val LOG_PATTERN = "%s -> %s"
        private const val LOG_WITH_TREAD_PATTERN = "%s|[%s] -> %s"

        private var shouldPrint: Boolean = true

        fun printLog(flag: Boolean) {
            shouldPrint = flag
        }
    }

    fun d(msg: String) {
        if (shouldPrint) {
            Log.d(tag, makeMsg(msg))
        }
    }

    fun e(throwable: Throwable, msg: String = "") {
        if (shouldPrint) {
            Log.e(tag, makeMsg(msg), throwable)
        }
    }

    private fun makeMsg(msg: String): String {
        return if (printThreadInfo) {
            LOG_WITH_TREAD_PATTERN.format(getLocation(), getThreadName(), msg)
        } else {
            LOG_PATTERN.format(getLocation(), msg)
        }
    }


    private fun getThreadName() = Thread.currentThread().let {
        THREAD_PATTERN.format(it.name, it.priority)
    }

    /**
     * Найти вызывающий код в Thread.stackTrace
     */
    private fun getLocation(): String {
        var found = false
        for (trace in Thread.currentThread().stackTrace) {
            try {
                if (found) {
                    if (!trace.className.startsWith(CustomLogger::class.java.name)) {
                        val clazz = Class.forName(trace.className)
                        val clazzName = getClassName(clazz)
                        val methodName = trace.methodName
                        val lineNumber = trace.lineNumber
                        return LOCATION_PATTERN.format(clazzName, methodName, lineNumber)
                    }
                } else if (trace.className.startsWith(CustomLogger::class.java.name)) {
                    found = true
                }
            } catch (ignored: ClassNotFoundException) {
            }
        }
        return ""
    }

    /**
     * Получить имя класса
     */
    private fun getClassName(clazz: Class<*>?): String {
        if (clazz == null)
            return ""
        return clazz.let {
            if (it.simpleName.isNotEmpty())
                it.simpleName
            else getClassName(it.enclosingClass)
        }
    }
}
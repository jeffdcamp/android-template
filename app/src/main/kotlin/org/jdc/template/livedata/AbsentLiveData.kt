package org.jdc.template.livedata

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

/**
 * A LiveData class that has `null` value.
 */
@Suppress("unused")
class AbsentLiveData<R>
private constructor() : LiveData<R>() {
    init {
        postValue(null)
    }

    companion object {
        fun <T> create(): LiveData<T> {
            return AbsentLiveData()
        }

        /**
         * null only absentValue
         */
        inline fun <R, T> switchMap(trigger: LiveData<T>, crossinline func: (T) -> LiveData<R>): LiveData<R> {
            return Transformations.switchMap(trigger) {
                when (it) {
                    null -> create()
                    else -> func(it)
                }
            }
        }

        /**
         * Single absentValues
         */
        inline fun <R, T> switchMap(trigger: LiveData<T>, absentValue: T, crossinline func: (T) -> LiveData<R>): LiveData<R> {
            return Transformations.switchMap(trigger) {
                when (it) {
                    null, absentValue -> create()
                    else -> func(it)
                }
            }
        }

        /**
         * Multiple absentValues
         */
        inline fun <R, T> switchMap(trigger: LiveData<T>, vararg absentValues: T, crossinline func: (T) -> LiveData<R>): LiveData<R> {
            return Transformations.switchMap(trigger) {
                when (it) {
                    null, in absentValues -> create()
                    else -> func(it)
                }
            }
        }
    }
}
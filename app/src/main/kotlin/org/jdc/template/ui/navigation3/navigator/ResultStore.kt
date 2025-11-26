package org.jdc.template.ui.navigation3.navigator

import androidx.annotation.VisibleForTesting
import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map

object ResultStore {
    private val lock = reentrantLock()
    private val resultMap = mutableMapOf<ResultKey, Any>()
    private val resultCallbacks = mutableMapOf<ResultKey, MutableList<ResultStoreCallback>>()

    @VisibleForTesting
    fun getResultValue(key: ResultKey): Any? = lock.withLock {
        resultMap[key]
    }

    inline fun <reified T : Any> getResult(key: ResultKey): T? {
        return getResultValue(key) as T?
    }

    fun setResult(key: ResultKey, value: Any?) {
        val callbacks = lock.withLock {
            if (value != null) {
                resultMap[key] = value
            } else {
                resultMap.remove(key)
            }
            resultCallbacks[key]
        }
        // Keep callback outside of lock in case a callback tries to acquire the lock.
        callbacks?.forEach { it.onResult(value) }
    }

    @VisibleForTesting
    fun removeResultValue(key: ResultKey): Any? {
        val (result, callbacks) = lock.withLock {
            val result = resultMap.remove(key)
            val callbacks = resultCallbacks[key]
            result to callbacks
        }
        // Keep callback outside of lock in case a callback tries to acquire the lock.
        callbacks?.forEach { it.onResult(null) }
        return result
    }


    inline fun <reified T : Any> removeResult(key: ResultKey): T? {
        return removeResultValue(key) as T?
    }

    @VisibleForTesting
    fun getAndRemoveResultValue(key: ResultKey): Any? {
        return removeResultValue(key)
    }

    inline fun <reified T : Any> getAndRemoveResult(key: ResultKey): T? {
        return removeResultValue(key) as T?
    }

    private fun registerListener(key: ResultKey, callback: ResultStoreCallback) {
        val result = lock.withLock {
            resultCallbacks.getOrPut(key) { mutableListOf() }.add(callback)
            resultMap[key]
        }
        // Keep callback outside of lock in case a callback tries to acquire the lock.
        callback.onResult(result)
    }

    private fun unregisterListener(key: ResultKey, callback: ResultStoreCallback) {
        lock.withLock {
            val list = resultCallbacks[key] ?: return
            list.remove(callback)
            if (list.isEmpty()) {
                resultCallbacks.remove(key)
            }
        }
    }

    @VisibleForTesting
    fun getResultValueFlow(key: ResultKey): Flow<Any?> {
        return callbackFlow {
            val callback = object : ResultStoreCallback {
                override fun onResult(result: Any?) {
                    trySend(result)
                }
            }
            registerListener(key, callback)

            awaitClose { unregisterListener(key, callback) }
        }

    }

    inline fun <reified T : Any> getResultFlow(key: ResultKey): Flow<T?> {
        return getResultValueFlow(key).map { it as? T }
    }
}

interface ResultKey

private interface ResultStoreCallback {
    fun onResult(result: Any?)
}

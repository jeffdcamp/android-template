package org.jdc.template.livedata

import android.arch.lifecycle.MutableLiveData
import android.support.annotation.WorkerThread
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.jdc.template.util.CoroutineContextProvider
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.experimental.CoroutineContext

/**
 * A LiveData class that can be invalidated & computed on demand.  The results of compute() is stored
 * in the internal LiveData until it becomes invalid (So that on LiveData.onActivate() the compute()
 * is not called excessively)
 *
 * Usage #1 (no updates needed):
 * fun loadMyData(): LiveData<MyData> {
 *     return object : CoroutineComputableLiveData<MyData>(cc.ui, cc.commonPool) {
 *           @WorkerThread
 *           suspend override fun compute(): MyData {
 *               // execute code to compute MyData
 *               return MyData()
 *           }
 *     }.liveData
 * }
 *
 * Usage #2 (support for updating):
 * private val myDataComputable =  object : CoroutineComputableLiveData<MyData>(cc.ui, cc.commonPool) {
 *           @WorkerThread
 *           suspend override fun compute(): MyData {
 *               // execute code to compute MyData
 *               return MyData()
 *           }
 *     }
 *
 * fun loadMyData(): LiveData<MyData> {
 *     return myDataComputable.liveData
 * }
 *
 * fun updateMyData() {
 *     myDataComputable.invalidate()
 * }
 *
 * @param <T> The type of the live data
 */
abstract class CoroutineComputableLiveData<T>(
        private val workerThreadContext: CoroutineContext = CommonPool,
        private val mainThreadContext: CoroutineContext = UI
) {

    constructor(cc: CoroutineContextProvider) : this(cc.commonPool, cc.ui)

    var liveData: MutableLiveData<T>
        private set

    private val invalid = AtomicBoolean(true)
    private val computing = AtomicBoolean(false)

    /**
     * Creates a computable live data which is computed when there are active observers.
     *
     *
     * It can also be invalidated via [.invalidate] which will result in a call to
     * [.compute] if there are active observers (or when they start observing)
     */
    init {
        liveData = object : MutableLiveData<T>() {
            override fun onActive() {
                launch(workerThreadContext) {
                    refresh()
                }
            }
        }
    }

    @WorkerThread
    suspend fun refresh() {
        var computed: Boolean
        do {
            computed = false
            // compute can happen only in 1 thread but no reason to lock others.
            if (computing.compareAndSet(false, true)) {
                // as long as it is invalid, keep computing.
                try {
                    var value: T? = null
                    while (invalid.compareAndSet(true, false)) {
                        computed = true
                        value = compute()
                    }
                    if (computed) {
                        liveData.postValue(value)
                    }
                } finally {
                    // release compute lock
                    computing.set(false)
                }
            }
            // check invalid after releasing compute lock to avoid the following scenario.
            // Thread A runs compute()
            // Thread A checks invalid, it is false
            // Main thread sets invalid to true
            // Thread B runs, fails to acquire compute lock and skips
            // Thread A releases compute lock
            // We've left invalid in set state. The check below recovers.
        } while (computed && invalid.get())
    }

    /**
     * Invalidates the LiveData.
     * - invalidation check always happens on the main thread
     *
     * When there are active observers, this will trigger a call to [.compute].
     */
    fun invalidate() = launch(mainThreadContext) {
        val isActive = liveData.hasActiveObservers()
        if (invalid.compareAndSet(false, true)) {
            if (isActive) {
                withContext(workerThreadContext) {
                    refresh()
                }
            }
        }
    }

    @WorkerThread
    abstract suspend fun compute(): T
}
@file:Suppress("unused")

package org.jdc.template.util

import kotlinx.coroutines.experimental.Job
import java.util.Arrays
import java.util.HashSet

class CompositeJob() {
    private var jobList = HashSet<Job>()

    constructor (vararg jobList: Job) : this() {
        this.jobList = HashSet(Arrays.asList(*jobList))
    }

    fun add(vararg newItems: Job) {
        synchronized(this) {
            cleanup()
            newItems.forEach {
                if (!it.isCompleted) {
                    jobList.add(it)
                }
            }
        }
    }

    fun remove(job: Job) {
        synchronized(this) {
            jobList.remove(job)
        }
    }

    fun cancelAndClearAll() {
        synchronized(this) {
            jobList.forEach {
                it.cancel()
            }

            jobList.clear()
        }
    }

    /**
     * Remove any futures that are done
     */
    private fun cleanup() {
        val iterator = jobList.iterator()
        while (iterator.hasNext()) {
            if (iterator.next().isCompleted) {
                iterator.remove()
            }
        }

    }

    fun isEmpty(): Boolean {
        synchronized(this) {
            return jobList.isEmpty()
        }
    }
}
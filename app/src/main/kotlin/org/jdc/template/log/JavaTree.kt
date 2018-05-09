package org.jdc.template.log

import timber.log.Timber

class JavaTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val logMessage: String
        if (tag != null && !tag.isEmpty()) {
            logMessage = "[$tag] $message"
        } else {
            logMessage = message
        }

        println(logMessage)
    }
}

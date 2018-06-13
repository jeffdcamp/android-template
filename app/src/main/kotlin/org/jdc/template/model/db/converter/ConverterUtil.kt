package org.jdc.template.model.db.converter

import timber.log.Timber

object ConverterUtil {
    fun <T: Enum<T>> getDefaultAndLogInvalidEnum(e: Exception, defaultValue: T): T {
        Timber.w(e, "Invalid enum found")
        return defaultValue
    }
}
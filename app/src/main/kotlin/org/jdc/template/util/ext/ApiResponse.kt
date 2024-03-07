@file:Suppress("unused")

package org.jdc.template.util.ext

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

sealed interface ApiResponse<T, E> {
    data class Success<T>(val data: T) : ApiResponse<T, Nothing>
    sealed interface Failure<E> : ApiResponse<Nothing, E> {
        open class Error<E>(val payload: E?, val message: String? = payload?.toString()) : Failure<E> {
            override fun equals(other: Any?): Boolean = other is Error<*> && payload == other.payload

            override fun hashCode(): Int {
                var result = 17
                result = 31 * result + payload.hashCode()
                return result
            }

            override fun toString(): String = message.orEmpty()
        }

        open class Exception(val throwable: Throwable) : Failure<Nothing> {
            val message: String? = throwable.message

            override fun equals(other: Any?): Boolean = other is Exception && throwable == other.throwable

            override fun hashCode(): Int {
                var result = 17
                result = 31 * result + throwable.hashCode()
                return result
            }

            override fun toString(): String = message.orEmpty()
        }
    }
}

/**
 * Returns the encapsulated data if this instance represents [ApiResponse.Success] or
 * returns null if it is [ApiResponse.Failure.Error] or [ApiResponse.Failure.Exception].
 * @return The encapsulated data or null.
 */
fun <T> ApiResponse<T, *>.getOrNull(): T? {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> null
    }
}

/**
 * Returns the encapsulated data if this instance represents [ApiResponse.Success] or
 * returns the [defaultValue] if it is [ApiResponse.Failure.Error] or [ApiResponse.Failure.Exception].
 * @return The encapsulated data or [defaultValue].
 */
fun <T> ApiResponse<T, *>.getOrElse(defaultValue: T): T {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> defaultValue
    }
}

/**
 * Returns the encapsulated data if this instance represents [ApiResponse.Success] or
 * invokes the lambda [defaultValue] that returns [T] if it is [ApiResponse.Failure.Error] or [ApiResponse.Failure.Exception].
 *
 * @return The encapsulated data or [defaultValue].
 */
inline fun <T> ApiResponse<T, *>.getOrElse(defaultValue: () -> T): T {
    return when (this) {
        is ApiResponse.Success -> data
        is ApiResponse.Failure -> defaultValue()
    }
}

/**
 * Returns the encapsulated data if this instance represents [ApiResponse.Success] or
 * throws the encapsulated Throwable exception if it is [ApiResponse.Failure.Error] or [ApiResponse.Failure.Exception].
 *
 * @throws RuntimeException if it is [ApiResponse.Failure.Error] or
 * the encapsulated Throwable exception if it is [ApiResponse.Failure.Exception.throwable]
 *
 * @return The encapsulated data.
 */
fun <T> ApiResponse<T, *>.getOrThrow(): T {
    when (this) {
        is ApiResponse.Success -> return data
        is ApiResponse.Failure.Error -> error(message())
        is ApiResponse.Failure.Exception -> throw throwable
    }
}

/**
 *  Returns true if this instance represents an [ApiResponse.Success].
 */
inline val ApiResponse<*, *>.isSuccess: Boolean
    get() = this is ApiResponse.Success

/**
 *  Returns true if this instance represents an [ApiResponse.Failure].
 */
inline val ApiResponse<*, *>.isFailure: Boolean
    get() = this is ApiResponse.Failure

/**
 *  Returns true if this instance represents an [ApiResponse.Failure.Error].
 */
inline val ApiResponse<*, *>.isError: Boolean
    get() = this is ApiResponse.Failure.Error

/**
 *  Returns true if this instance represents an [ApiResponse.Failure.Exception].
 */
inline val ApiResponse<*, *>.isException: Boolean
    get() = this is ApiResponse.Failure.Exception

/**
 *  Returns The error message or null depending on the type of [ApiResponse].
 */
inline val ApiResponse<*, *>.messageOrNull: String?
    get() = when (this) {
        is ApiResponse.Failure.Error -> message
        is ApiResponse.Failure.Exception -> message
        else -> null
    }

/**
 * A scope function that would be executed for handling successful responses if the request succeeds.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Success] if the request succeeds.
 * @return The original [ApiResponse].
 */
@Suppress("OutdatedDocumentation") // Detekt does not seem to work well with this function for documentation
@OptIn(ExperimentalContracts::class)
inline fun <T> ApiResponse<T, *>.onSuccess(
    crossinline onResult: ApiResponse.Success<T>.() -> Unit,
): ApiResponse<T, *> {
    contract { callsInPlace(onResult, InvocationKind.AT_MOST_ONCE) }
    if (this is ApiResponse.Success) {
        onResult(this)
    }
    return this
}

/**
 * A function that would be executed for handling error responses if the request failed or get an exception.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Failure] if the request failed or get an exception.
 * @return The original [ApiResponse].
 */
@Suppress("OutdatedDocumentation") // Detekt does not seem to work well with this function for documentation
@OptIn(ExperimentalContracts::class)
inline fun <T> ApiResponse<T, *>.onFailure(
    crossinline onResult: ApiResponse.Failure<*>.() -> Unit,
): ApiResponse<T, *> {
    contract { callsInPlace(onResult, InvocationKind.AT_MOST_ONCE) }
    if (this is ApiResponse.Failure) {
        onResult(this)
    }
    return this
}

/**
 * A scope function that would be executed for handling error responses if the request failed.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Failure.Exception] if the request failed.
 * @return The original [ApiResponse].
 */
@Suppress("OutdatedDocumentation") // Detekt does not seem to work well with this function for documentation
@OptIn(ExperimentalContracts::class)
inline fun <E> ApiResponse<*, E>.onError(
    crossinline onResult: ApiResponse.Failure.Error<E>.() -> Unit,
): ApiResponse<*, E> {
    contract { callsInPlace(onResult, InvocationKind.AT_MOST_ONCE) }
    if (this is ApiResponse.Failure.Error) {
        onResult(this)
    }
    return this
}

/**
 * A scope function that would be executed for handling exception responses if the request get an exception.
 *
 * @param onResult The receiver function that receiving [ApiResponse.Failure.Exception] if the request get an exception.
 * @return The original [ApiResponse].
 */
@Suppress("OutdatedDocumentation") // Detekt does not seem to work well with this function for documentation
@OptIn(ExperimentalContracts::class)
inline fun ApiResponse<*, *>.onException(
    crossinline onResult: ApiResponse.Failure.Exception.() -> Unit,
): ApiResponse<*, *> {
    contract { callsInPlace(onResult, InvocationKind.AT_MOST_ONCE) }
    if (this is ApiResponse.Failure.Exception) {
        onResult(this)
    }
    return this
}

/**
 * Returns an error message from the [ApiResponse.Failure] that consists of the localized message.
 * @return An error message from the [ApiResponse.Failure].
 */
fun ApiResponse.Failure<*>.message(): String {
    return when (this) {
        is ApiResponse.Failure.Error -> message()
        is ApiResponse.Failure.Exception -> message()
    }
}

/**
 * Returns an error message from the [ApiResponse.Failure.Error] that consists of the status and error response.
 * @return An error message from the [ApiResponse.Failure.Error].
 */
fun ApiResponse.Failure.Error<*>.message(): String = toString()

/**
 * Returns an error message from the [ApiResponse.Failure.Exception] that consists of the localized message.
 * @return An error message from the [ApiResponse.Failure.Exception].
 */
fun ApiResponse.Failure.Exception.message(): String = toString()

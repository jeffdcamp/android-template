package org.jdc.template.util.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isInstanceOf
import assertk.assertions.isNull
import assertk.assertions.isTrue
import assertk.fail
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.resources.get
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.resources.Resource
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import org.jdc.template.util.ext.cacheHeaders
import org.jdc.template.util.ext.executeSafelyCached
import org.junit.jupiter.api.Test

class CacheApiResponseTest {

    @Test
    fun `test CacheApiResponse Success Etag Data`() = runTest {
        val mockEngine = MockEngine {
            assertThat(it.headers[HttpHeaders.IfNoneMatch]).isEqualTo("abc")
            respond(
                content = """{"status":"success"}""",
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"), HttpHeaders.ETag to listOf("def")),
                status = HttpStatusCode.OK,
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoCacheTest, out Unit> = client.executeSafelyCached({ get(CacheTestResource) { cacheHeaders("abc", null) } }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Success::class)
        assertThat(response.getOrNull()).isEqualTo(DtoCacheTest("success"))
        assertThat(response.getOrThrow()).isEqualTo(DtoCacheTest("success"))
        assertThat(response.messageOrNull).isNull()
        assertThat(response.isSuccess).isTrue()
        assertThat(response.isFailure).isFalse()
        assertThat(response.isError).isFalse()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            assertThat(data).isEqualTo(DtoCacheTest("success"))
            assertThat(etag).isEqualTo("def")
        }.onFailure {
            fail("Should not be called")
        }.onError {
            fail("Should not be called")
        }.onException {
            fail("Should not be called")
        }
    }

    @Test
    fun `test CacheApiResponse Success Etag No Data`() = runTest {
        val mockEngine = MockEngine {
            assertThat(it.headers[HttpHeaders.IfNoneMatch]).isEqualTo("def")
            respond(
                content = "",
                headers = headersOf(HttpHeaders.ETag to listOf("def")),
                status = HttpStatusCode.NotModified,
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoCacheTest, out Unit> = client.executeSafelyCached({ get(CacheTestResource) { cacheHeaders("def", null) } }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Success::class)
        assertThat(response.getOrNull()).isNull()
        assertThat(response.getOrThrow()).isNull()
        assertThat(response.messageOrNull).isNull()
        assertThat(response.isSuccess).isTrue()
        assertThat(response.isFailure).isFalse()
        assertThat(response.isError).isFalse()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            assertThat(data).isNull()
            assertThat(etag).isEqualTo("def")
        }.onFailure {
            fail("Should not be called")
        }.onError {
            fail("Should not be called")
        }.onException {
            fail("Should not be called")
        }
    }

    @Test
    fun `test CacheApiResponse Success LastModified Data`() = runTest {
        val mockEngine = MockEngine {
            assertThat(it.headers[HttpHeaders.IfModifiedSince]).isEqualTo("abc")

            respond(
                content = """{"status":"success"}""",
                headers = headersOf(HttpHeaders.ContentType to listOf("application/json"), HttpHeaders.LastModified to listOf("def")),
                status = HttpStatusCode.OK,
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoCacheTest, out Unit> = client.executeSafelyCached({ get(CacheTestResource) { cacheHeaders(null, "abc") } }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Success::class)
        assertThat(response.getOrNull()).isEqualTo(DtoCacheTest("success"))
        assertThat(response.getOrThrow()).isEqualTo(DtoCacheTest("success"))
        assertThat(response.messageOrNull).isNull()
        assertThat(response.isSuccess).isTrue()
        assertThat(response.isFailure).isFalse()
        assertThat(response.isError).isFalse()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            assertThat(data).isEqualTo(DtoCacheTest("success"))
            assertThat(lastModified).isEqualTo("def")
        }.onFailure {
            fail("Should not be called")
        }.onError {
            fail("Should not be called")
        }.onException {
            fail("Should not be called")
        }
    }

    @Test
    fun `test CacheApiResponse Success LastModified No Data`() = runTest {
        val mockEngine = MockEngine {
            assertThat(it.headers[HttpHeaders.IfModifiedSince]).isEqualTo("def")
            respond(
                content = "",
                headers = headersOf(HttpHeaders.LastModified to listOf("def")),
                status = HttpStatusCode.NotModified,
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoCacheTest, out Unit> = client.executeSafelyCached({ get(CacheTestResource) { cacheHeaders(null, "def") } }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Success::class)
        assertThat(response.getOrNull()).isNull()
        assertThat(response.getOrThrow()).isNull()
        assertThat(response.messageOrNull).isNull()
        assertThat(response.isSuccess).isTrue()
        assertThat(response.isFailure).isFalse()
        assertThat(response.isError).isFalse()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            assertThat(data).isNull()
            assertThat(lastModified).isEqualTo("def")
        }.onFailure {
            fail("Should not be called")
        }.onError {
            fail("Should not be called")
        }.onException {
            fail("Should not be called")
        }
    }

    @Test
    fun `test CacheApiResponse Error Forbidden`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"status":"forbidden"}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                status = HttpStatusCode.Forbidden,
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoTest, out Unit> = client.executeSafelyCached({ get(TestResource) }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Failure.Error.Forbidden::class)
        assertThat(response.getOrNull()).isNull()
        try {
            assertThat(response.getOrThrow())
            fail("Should throw exception")
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(RuntimeException::class)
            assertThat(e.message).isEqualTo("Error executing service call: GET http://localhost/test (403 Forbidden)")
        }
        assertThat(response.messageOrNull).isEqualTo("Error executing service call: GET http://localhost/test (403 Forbidden)")
        assertThat(response.isSuccess).isFalse()
        assertThat(response.isFailure).isTrue()
        assertThat(response.isError).isTrue()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            fail("Should not be called")
        }.onFailure {
            assertThat(message()).isEqualTo("Error executing service call: GET http://localhost/test (403 Forbidden)")
        }.onError {
            assertThat(message).isEqualTo("Error executing service call: GET http://localhost/test (403 Forbidden)")
        }.onException {
            fail("Should not be called")
        }

        assertThat(response.getOrElse(DtoTest("default"))).isEqualTo(DtoTest("default"))
        assertThat(response.getOrElse { DtoTest("default") }).isEqualTo(DtoTest("default"))
    }

    @Test
    fun `test CacheApiResponse Error NoToken`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"status":"no token"}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                status = HttpStatusCode(480, "No Token"),
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoTest, out Unit> = client.executeSafelyCached({ get(TestResource) }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Failure.Error.NoToken::class)
        assertThat(response.getOrNull()).isNull()
        try {
            assertThat(response.getOrThrow())
            fail("Should throw exception")
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(RuntimeException::class)
            assertThat(e.message).isEqualTo("Error executing service call: GET http://localhost/test (480 No Token)")
        }
        assertThat(response.messageOrNull).isEqualTo("Error executing service call: GET http://localhost/test (480 No Token)")
        assertThat(response.isSuccess).isFalse()
        assertThat(response.isFailure).isTrue()
        assertThat(response.isError).isTrue()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            fail("Should not be called")
        }.onFailure {
            assertThat(message()).isEqualTo("Error executing service call: GET http://localhost/test (480 No Token)")
        }.onError {
            assertThat(message).isEqualTo("Error executing service call: GET http://localhost/test (480 No Token)")
        }.onException {
            fail("Should not be called")
        }
    }

    @Test
    fun `test CacheApiResponse Error Request No Details`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"status":"error"}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                status = HttpStatusCode.BadRequest,
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoTest, out Unit> = client.executeSafelyCached({ get(TestResource) }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Failure.Error.Client::class)
        assertThat(response.getOrNull()).isNull()
        try {
            assertThat(response.getOrThrow())
            fail("Should throw exception")
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(RuntimeException::class)
            assertThat(e.message).isEqualTo("Error executing service call: GET http://localhost/test (400 Bad Request)")
        }
        assertThat(response.messageOrNull).isEqualTo("Error executing service call: GET http://localhost/test (400 Bad Request)")
        assertThat(response.isSuccess).isFalse()
        assertThat(response.isFailure).isTrue()
        assertThat(response.isError).isTrue()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            fail("Should not be called")
        }.onFailure {
            assertThat(message()).isEqualTo("Error executing service call: GET http://localhost/test (400 Bad Request)")
        }.onError {
            assertThat(message).isEqualTo("Error executing service call: GET http://localhost/test (400 Bad Request)")
        }.onException {
            fail("Should not be called")
        }
    }

    @Test
    fun `test CacheApiResponse Error Request With Details`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"status":"error"}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                status = HttpStatusCode.BadRequest,
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoTest, out DtoTest> = client.executeSafelyCached(
            apiCall = { get(TestResource) },
            mapClientError = { CacheApiResponse.Failure.Error.Client(it.body<DtoTest>()) },
            mapSuccess = { it.body() },
        )

        assertThat(response).isInstanceOf(CacheApiResponse.Failure.Error.Client::class)
        assertThat(response.getOrNull()).isNull()
        try {
            assertThat(response.getOrThrow())
            fail("Should throw exception")
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(RuntimeException::class)
            assertThat(e.message).isEqualTo("DtoTest(status=error)")
        }
        assertThat(response.messageOrNull).isEqualTo("DtoTest(status=error)")
        assertThat(response.isSuccess).isFalse()
        assertThat(response.isFailure).isTrue()
        assertThat(response.isError).isTrue()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            fail("Should not be called")
        }.onFailure {
            assertThat(message()).isEqualTo("DtoTest(status=error)")
        }.onError {
            assertThat(message).isEqualTo("DtoTest(status=error)")
        }.onException {
            fail("Should not be called")
        }

        assertThat((response as CacheApiResponse.Failure.Error.Client).details).isEqualTo(DtoTest("error"))
    }

    @Test
    fun `test CacheApiResponse Error Server`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"status":"error"}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                status = HttpStatusCode.InternalServerError,
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoTest, out Unit> = client.executeSafelyCached({ get(TestResource) }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Failure.Error.Server::class)
        assertThat(response.getOrNull()).isNull()
        try {
            assertThat(response.getOrThrow())
            fail("Should throw exception")
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(RuntimeException::class)
            assertThat(e.message).isEqualTo("Error executing service call: GET http://localhost/test (500 Internal Server Error)")
        }
        assertThat(response.messageOrNull).isEqualTo("Error executing service call: GET http://localhost/test (500 Internal Server Error)")
        assertThat(response.isSuccess).isFalse()
        assertThat(response.isFailure).isTrue()
        assertThat(response.isError).isTrue()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            fail("Should not be called")
        }.onFailure {
            assertThat(message()).isEqualTo("Error executing service call: GET http://localhost/test (500 Internal Server Error)")
        }.onError {
            assertThat(message).isEqualTo("Error executing service call: GET http://localhost/test (500 Internal Server Error)")
        }.onException {
            fail("Should not be called")
        }
    }

    @Test
    fun `test CacheApiResponse Error Unknown`() = runTest {
        val mockEngine = MockEngine {
            respond(
                content = """{"status":"error"}""",
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                status = HttpStatusCode(600, "Unknown"),
            )
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoTest, out Unit> = client.executeSafelyCached({ get(TestResource) }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Failure.Error.Unknown::class)
        assertThat(response.getOrNull()).isNull()
        try {
            assertThat(response.getOrThrow())
            fail("Should throw exception")
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(RuntimeException::class)
            assertThat(e.message).isEqualTo("Error executing service call: GET http://localhost/test (600 Unknown)")
        }
        assertThat(response.messageOrNull).isEqualTo("Error executing service call: GET http://localhost/test (600 Unknown)")
        assertThat(response.isSuccess).isFalse()
        assertThat(response.isFailure).isTrue()
        assertThat(response.isError).isTrue()
        assertThat(response.isException).isFalse()

        response.onSuccess {
            fail("Should not be called")
        }.onFailure {
            assertThat(message()).isEqualTo("Error executing service call: GET http://localhost/test (600 Unknown)")
        }.onError {
            assertThat(message).isEqualTo("Error executing service call: GET http://localhost/test (600 Unknown)")
        }.onException {
            fail("Should not be called")
        }
    }

    @Test
    fun `test CacheApiResponse Exception`() = runTest {
        val mockEngine = MockEngine {
            error("Test exception")
        }
        val client = TestHttpClientProvider.getTestClient(mockEngine)

        val response: CacheApiResponse<out DtoTest, out Unit> = client.executeSafelyCached({ get(TestResource) }) { it.body() }

        assertThat(response).isInstanceOf(CacheApiResponse.Failure.Exception::class)
        assertThat(response.getOrNull()).isNull()
        try {
            assertThat(response.getOrThrow())
            fail("Should throw exception")
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(IllegalStateException::class)
            assertThat(e.message).isEqualTo("Test exception")
        }
        assertThat(response.messageOrNull).isEqualTo("Test exception")
        assertThat(response.isSuccess).isFalse()
        assertThat(response.isFailure).isTrue()
        assertThat(response.isError).isFalse()
        assertThat(response.isException).isTrue()

        response.onSuccess {
            fail("Should not be called")
        }.onFailure {
            assertThat(message()).isEqualTo("Test exception")
        }.onError {
            fail("Should not be called")
        }.onException {
            assertThat(throwable).isInstanceOf(Exception::class)
            assertThat(throwable.message).isEqualTo("Test exception")
        }

    }
}

@Serializable
private data class DtoCacheTest(    val status: String)
@Resource("test")
private object CacheTestResource

package org.jdc.template.datasource.webservice.colors

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.jdc.template.inject.TestInjector
import org.jdc.template.model.webservice.colors.ColorService
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ColorServiceTest {

    @Inject lateinit var colorService: ColorService
    @Inject lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        TestInjector.init()
        TestInjector.get().inject(this)
    }

    @Test
    fun getColors() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(COLORS_RESPONSE))

        val request = colorService.colors().execute()
        Assert.assertTrue(request.isSuccessful)

        val colors = request.body()
        Assert.assertNotNull(colors)
        Assert.assertEquals(1, colors!!.colors.size)

        val color = colors.colors[0]
        Assert.assertEquals("White", color.colorName)
        Assert.assertEquals("#FFFFFF", color.hexValue)
    }

    @Test
    fun failedNetwork() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("""{"error": "Oh No!" }"""))
        val request = colorService.colors().execute()

        Assert.assertFalse(request.isSuccessful)
    }

    @Test(expected = SocketTimeoutException::class)
    fun timeoutThrowsException() {
        mockWebServer.enqueue(
                MockResponse().setBody(COLORS_RESPONSE)
                        .throttleBody(8, 1, TimeUnit.SECONDS))

        colorService.colors().execute()
    }

    companion object {
        const val COLORS_RESPONSE = """
            {
                "colors": [
                    {
                       "colorName": "White",
                       "hexValue": "#FFFFFF"
                    }
                ]
            }
        """
    }
}
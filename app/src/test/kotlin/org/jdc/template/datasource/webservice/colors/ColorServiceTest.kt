package org.jdc.template.datasource.webservice.colors

import com.google.common.truth.Truth.assertThat
import dagger.Component
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.jdc.template.LoggingUtil
import org.jdc.template.inject.CommonTestModule
import org.jdc.template.model.repository.IndividualRepositoryTestModule
import org.jdc.template.model.webservice.colors.ColorService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.inject.Singleton

class ColorServiceTest {

    @Inject
    lateinit var colorService: ColorService

    @Inject
    lateinit var mockWebServer: MockWebServer

    @BeforeEach
    fun setup() {
        LoggingUtil.setupSingleLineLogging(true)

        val component = DaggerColorServiceTestComponent.builder().build()
        component.inject(this)
    }

    @Test
    fun getColors() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(COLORS_RESPONSE))

        val response = colorService.colors()
        assertThat(response.isSuccessful).isTrue()

        val colors = response.body()
        checkNotNull(colors)
        assertThat(colors).isNotNull()
        assertThat(colors.colors.size).isEqualTo(1)

        val color = colors.colors.first()
        assertThat(color.colorName).isEqualTo("White")
        assertThat(color.hexValue).isEqualTo("#FFFFFF")
    }

    @Test
    fun failedNetwork() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(500).setBody("""{"error": "Oh No!" }"""))
        val response = colorService.colors()

        assertThat(response.isSuccessful).isFalse()
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

@Singleton
@Component(modules = [CommonTestModule::class, IndividualRepositoryTestModule::class])
interface ColorServiceTestComponent {
    fun inject(test: ColorServiceTest)
}
package konsist

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import kotlin.test.Test

class ArchitectureTest {
    @Test
    fun `clean architecture layers have correct dependencies`() {
        Konsist
            .scopeFromProject()
            .assertArchitecture {
                // Define layers
                val domain = Layer("Domain", "${KonsistConts.APP_PACKAGE_NAME}.model.domain..")
                val ux = Layer("UX", "${KonsistConts.APP_PACKAGE_NAME}.ux..")
                val data = Layer("Repository", "${KonsistConts.APP_PACKAGE_NAME}.model.repository..")

                // Define architecture assertions
                domain.dependsOnNothing()
                ux.dependsOn(domain)
                data.dependsOn(domain)
            }
    }
}
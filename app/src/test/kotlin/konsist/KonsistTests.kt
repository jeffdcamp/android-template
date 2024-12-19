package konsist

import androidx.annotation.VisibleForTesting
import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.functions
import com.lemonappdev.konsist.api.ext.list.modifierprovider.withPublicModifier
import com.lemonappdev.konsist.api.ext.list.print
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withoutAnnotationOf
import kotlin.test.Test

class KonsistTests {
    @Test
    fun `classes with 'UseCase' suffix should reside in 'usecase' package`() {
        Konsist.scopeFromProject()
            .classes()
            .withNameEndingWith("UseCase")
            .functions()
            .withoutAnnotationOf(VisibleForTesting::class)
            .print("xxx-")
            .withPublicModifier()
            .print("yyy-")
    }
}
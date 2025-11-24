package org.jdc.template.ui.navigation3

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.jdc.template.ux.about.AboutRoute
import org.jdc.template.ux.about.typography.TypographyRoute
import org.jdc.template.ux.acknowledgement.AcknowledgmentsRoute
import org.jdc.template.ux.chat.ChatRoute
import org.jdc.template.ux.chats.ChatsRoute
import org.jdc.template.ux.directory.DirectoryRoute
import org.jdc.template.ux.individual.IndividualRoute
import org.jdc.template.ux.individualedit.IndividualEditRoute
import org.jdc.template.ux.settings.SettingsRoute

val NavKeySerializerModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(AboutRoute::class)
        subclass(AcknowledgmentsRoute::class)
        subclass(ChatRoute::class)
        subclass(ChatsRoute::class)
        subclass(DirectoryRoute::class)
        subclass(IndividualRoute::class)
        subclass(IndividualEditRoute::class)
        subclass(SettingsRoute::class)
        subclass(TypographyRoute::class)
    }
}

private val NavKeyJson = Json {
    serializersModule = NavKeySerializerModule
    ignoreUnknownKeys = true
}

object NavKeyBridgeSerializer : KSerializer<NavKey> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NavKeyBridge", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: NavKey) {
        val string = NavKeyJson.encodeToString(
            PolymorphicSerializer(NavKey::class),
            value
        )
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): NavKey {
        // We read the string back and decode it using the module
        val string = decoder.decodeString()
        return NavKeyJson.decodeFromString(
            PolymorphicSerializer(NavKey::class),
            string
        )
    }
}

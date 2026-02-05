package org.jdc.template.shared.util.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class UriTest {
    @Test
    fun parseUri() {
        val uri = Uri.parse("/item/123?lang=eng")

        assertThat(uri.path).isEqualTo("/item/123")
        assertThat(uri.getQueryParameter("lang")).isEqualTo("eng")
        assertThat(uri.scheme).isEqualTo(null)
        assertThat(uri.toString()).isEqualTo("/item/123?lang=eng")
    }

    @Test
    fun parseUri2() {
        val uri = Uri.parse("myapp://content/item/123?lang=eng")

        assertThat(uri.path).isEqualTo("/item/123")
        assertThat(uri.getQueryParameter("lang")).isEqualTo("eng")
        assertThat(uri.scheme).isEqualTo("myapp")
        assertThat(uri.toString()).isEqualTo("myapp://content/item/123?lang=eng")
    }

    @Test
    fun modify() {
        val original = Uri.parse("/search?q=kotlin")

        val modified = original.newBuilder()
            .appendPath("advanced")
            .appendQueryParameter("page", "1")
            .setQueryParameter("q", "kmp")
            .build()

        assertThat(original.toString()).isEqualTo("/search?q=kotlin")
        assertThat(modified.path).isEqualTo("/search/advanced")
        assertThat(modified.getQueryParameter("q")).isEqualTo("kmp")
        assertThat(modified.getQueryParameter("page")).isEqualTo("1")
        assertThat(modified.scheme).isEqualTo(null)
    }

    @Test
    fun modify2() {
        val original = Uri.parse("myapp://content/search?q=kotlin")

        val modified = original.newBuilder()
            .appendPath("advanced")
            .appendQueryParameter("page", "1")
            .setQueryParameter("q", "kmp")
            .build()

        assertThat(original.toString()).isEqualTo("myapp://content/search?q=kotlin")
        assertThat(modified.scheme).isEqualTo("myapp")
        assertThat(modified.host).isEqualTo("content")
        assertThat(modified.path).isEqualTo("/search/advanced")
        assertThat(modified.getQueryParameter("q")).isEqualTo("kmp")
        assertThat(modified.getQueryParameter("page")).isEqualTo("1")
    }

    @Test
    fun newUri() {
        val newUri = Uri.Builder()
            .path("/v1/users")
            .appendQueryParameter("id", "55")
            .fragment("top")
            .build()

        assertThat(newUri.path).isEqualTo("/v1/users")
        assertThat(newUri.getQueryParameter("id")).isEqualTo("55")
        assertThat(newUri.fragment).isEqualTo("top")
        assertThat(newUri.scheme).isEqualTo(null)
        assertThat(newUri.toString()).isEqualTo("/v1/users?id=55#top")
    }

    @Test
    fun newCustomSchemaUrl() {
        val newUri = Uri.Builder()
            .scheme("myapp")
            .host("content")
            .path("/v1/users")
            .appendQueryParameter("id", "55")
            .fragment("top")
            .build()

        assertThat(newUri.scheme).isEqualTo("myapp")
        assertThat(newUri.host).isEqualTo("content")
        assertThat(newUri.path).isEqualTo("/v1/users")
        assertThat(newUri.getQueryParameter("id")).isEqualTo("55")
        assertThat(newUri.fragment).isEqualTo("top")
        assertThat(newUri.toString()).isEqualTo("myapp://content/v1/users?id=55#top")
    }
}

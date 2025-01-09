package com.yonatankarp.cat.fact.client.adopters

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create
import kotlin.test.assertEquals

internal class ApiCatFactProviderTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var catFactClient: CatFactClient
    private lateinit var apiCatFactProvider: ApiCatFactProvider

    @BeforeEach
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        catFactClient =
            Retrofit
                .Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(OkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
                .build()
                .create()
        apiCatFactProvider = ApiCatFactProvider(jacksonObjectMapper(), catFactClient)
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should get a cat fact successfully`() =
        runTest {
            mockWebServer.enqueue(MockResponse().setBody(CAT_FACT_RESPONSE))

            val catFact = apiCatFactProvider.get()

            assertEquals(1, catFact.size)
            assertEquals(CAT_FACT, catFact.first().value)
        }

    companion object {
        private const val CAT_FACT =
            "Cats are the most popular pet in the United States: There are 88 million pet cats and 74 million dogs."
        private const val CAT_FACT_LENGTH = 103
        private const val CAT_FACT_RESPONSE = """{"fact":"$CAT_FACT","length": $CAT_FACT_LENGTH}"""
    }
}

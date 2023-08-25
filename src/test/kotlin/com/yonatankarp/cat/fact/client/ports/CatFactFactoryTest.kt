package com.yonatankarp.cat.fact.client.ports

import com.fasterxml.jackson.databind.ObjectMapper
import com.yonatankarp.cat.fact.client.adopters.ApiCatFactProvider
import com.yonatankarp.cat.fact.client.adopters.MockCatFactProvider
import org.junit.jupiter.api.Test

class CatFactFactoryTest {
    @Test
    fun `should get api cat fact provider`() {
        val catFactProvider = CatFactFactory.getInstance(ProviderType.API, ObjectMapper())
        assert(catFactProvider is ApiCatFactProvider)
    }

    @Test
    fun `should get mock cat fact provider`() {
        val catFactProvider = CatFactFactory.getInstance(ProviderType.MOCK, ObjectMapper())
        assert(catFactProvider is MockCatFactProvider)
    }
}

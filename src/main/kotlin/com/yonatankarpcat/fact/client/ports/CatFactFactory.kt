package com.yonatankarpcat.fact.client.ports

import com.fasterxml.jackson.databind.ObjectMapper
import com.yonatankarpcat.fact.client.adopters.ApiCatFactProvider
import com.yonatankarpcat.fact.client.adopters.MockCatFactProvider
import com.yonatankarpcat.fact.client.ports.ProviderType.API
import com.yonatankarpcat.fact.client.ports.ProviderType.MOCK

data object CatFactFactory {
    fun getInstance(type: ProviderType, objectMapper: ObjectMapper): CatFactProvider =
        when (type) {
            API -> ApiCatFactProvider(objectMapper)
            MOCK -> MockCatFactProvider()
        }
}

enum class ProviderType {
    API,
    MOCK,
}

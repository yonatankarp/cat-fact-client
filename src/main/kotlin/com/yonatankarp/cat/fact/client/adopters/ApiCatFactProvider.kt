package com.yonatankarp.cat.fact.client.adopters

import com.fasterxml.jackson.databind.ObjectMapper
import com.yonatankarp.cat.fact.client.ports.CatFactProvider
import com.yonatankarp.cat.fact.client.ports.Fact
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create

private const val API_BASE_URL = "https://catfact.ninja/"

internal class ApiCatFactProvider(
    objectMapper: ObjectMapper,
    private var client: CatFactClient =
        Retrofit
            .Builder()
            .baseUrl(API_BASE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
            .create<CatFactClient>(),
) : CatFactProvider {
    override suspend fun get(numberOfFacts: Int): Set<Fact> =
        coroutineScope {
            (1..numberOfFacts)
                .map {
                    async { client.fact() }
                }.awaitAll()
                .map { Fact(it.fact) }
                .toSet()
        }
}

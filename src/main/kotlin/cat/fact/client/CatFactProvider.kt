package cat.fact.client

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.create

private const val API_BASE_URL = "https://catfact.ninja/"

class CatFactProvider(objectMapper: ObjectMapper) {

    private var client = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .build()
        .create<CatFactClient>()

    /**
     * Get a random cat facts.
     *
     * @param numberOfFacts the number of facts to get, by default only 1 fact is returned.
     * @return a set of cat facts.
     */
    suspend fun get(numberOfFacts: Int = 1): Set<Fact> =
        coroutineScope {
            (1..numberOfFacts).map {
                async { client.fact() }
            }.awaitAll()
                .map { Fact(it.fact) }
                .toSet()
        }
}

package com.yonatankarp.cat.fact.client.ports

interface CatFactProvider {
    /**
     * Get a random cat facts.
     *
     * @param numberOfFacts the number of facts to get, by default only 1 fact is returned.
     * @return a set of cat facts.
     */
    suspend fun get(numberOfFacts: Int = 1): Set<Fact>
}

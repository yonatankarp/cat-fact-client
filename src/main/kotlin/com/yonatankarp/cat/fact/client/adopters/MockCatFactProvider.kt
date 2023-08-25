package com.yonatankarp.cat.fact.client.adopters

import com.yonatankarp.cat.fact.client.ports.CatFactProvider
import com.yonatankarp.cat.fact.client.ports.Fact

internal class MockCatFactProvider : CatFactProvider {
    override suspend fun get(numberOfFacts: Int) =
        (1..numberOfFacts).map {
            Fact("Fact #$it")
        }.toSet()
}

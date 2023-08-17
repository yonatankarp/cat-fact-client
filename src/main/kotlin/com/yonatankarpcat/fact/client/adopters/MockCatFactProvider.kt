package com.yonatankarpcat.fact.client.adopters

import com.yonatankarpcat.fact.client.ports.CatFactProvider
import com.yonatankarpcat.fact.client.ports.Fact

internal class MockCatFactProvider : CatFactProvider {
    override suspend fun get(numberOfFacts: Int) =
        (1..numberOfFacts).map {
            Fact("Fact #$it")
        }.toSet()
}

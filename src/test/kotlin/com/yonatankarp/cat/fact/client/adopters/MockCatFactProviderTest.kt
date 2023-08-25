package com.yonatankarp.cat.fact.client.adopters

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

internal class MockCatFactProviderTest {

    private val mockCatFactProvider: MockCatFactProvider = MockCatFactProvider()

    @Test
    fun `should return mocked facts`() = runTest {
        // Given
        val numberOfFacts = 3

        // When
        val facts = mockCatFactProvider.get(numberOfFacts)

        // Then
        assert(facts.size == numberOfFacts)
    }

    @Test
    fun `should return empty set when number of facts is negative`() = runTest {
        // Given
        val numberOfFacts = -1

        // When
        val facts = mockCatFactProvider.get(numberOfFacts)

        // Then
        assert(facts.isEmpty())
    }

    @Test
    fun `should return empty set when number of facts is zero`() = runTest {
        // Given
        val numberOfFacts = 0

        // When
        val facts = mockCatFactProvider.get(numberOfFacts)

        // Then
        assert(facts.isEmpty())
    }
}

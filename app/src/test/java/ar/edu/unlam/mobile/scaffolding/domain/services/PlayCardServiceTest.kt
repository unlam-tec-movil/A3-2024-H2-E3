package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.domain.models.CardType
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import org.junit.Assert.*
import org.junit.Test

class PlayCardServiceTest {
    private val playCardService = PlayCardService()

    @Test
    fun `draw card returns a valid card`() {
        val card = playCardService.drawCard()

        assertNotNull(card)
        assertTrue(CardValue.entries.contains(card.value))
        assertTrue(CardType.entries.contains(card.type))
    }
}

package ar.edu.unlam.mobile.scaffolding.fakes

import ar.edu.unlam.mobile.scaffolding.domain.models.CardType
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.domain.usecases.PlayCardUseCases

class PlayCardUseCasesFake : PlayCardUseCases {
    private var cardToDraw: PlayCard? = null

    override fun drawCard(): PlayCard {
        return cardToDraw ?: PlayCard(
            CardValue.TWO,
            CardType.SPADES,
        ) // Valor por defecto si no se define
    }

    fun setCardToDraw(card: PlayCard) {
        cardToDraw = card
    }
}

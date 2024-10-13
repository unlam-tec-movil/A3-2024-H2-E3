package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.domain.models.CardType
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.domain.usecases.PlayCardUseCases

class PlayCardService : PlayCardUseCases {

    override fun drawCard(): PlayCard {
        return PlayCard(
            CardValue.entries.random(),
            CardType.entries.random()
        )
    }
}
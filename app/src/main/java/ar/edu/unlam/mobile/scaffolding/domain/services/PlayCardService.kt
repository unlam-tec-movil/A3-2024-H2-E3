package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.domain.models.CardType
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.domain.usecases.PlayCardUseCases
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayCardService
    @Inject
    constructor() : PlayCardUseCases {
        override fun drawCard(): PlayCard =
            PlayCard(
                CardValue.entries.random(),
                CardType.entries.random(),
            )
    }

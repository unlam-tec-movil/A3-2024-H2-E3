package ar.edu.unlam.mobile.scaffolding.fakes

import ar.edu.unlam.mobile.scaffolding.domain.models.CardType
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.PlayCardUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class GameUseCasesFake : GameUseCases {
    private val diceFlow = MutableSharedFlow<Pair<Dice, Dice>>()

    override fun getRandomDicePair(): Flow<Pair<Dice, Dice>> = diceFlow

    override fun getDiceThrowResult(dicePair: Pair<Dice, Dice>): Int = dicePair.first.value + dicePair.second.value

    override fun getRandomDicePairForCPU(): Flow<Pair<Dice, Dice>> = diceFlow

    fun emitDicePair(dicePair: Pair<Dice, Dice>) {
        diceFlow.tryEmit(dicePair)
    }

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
}

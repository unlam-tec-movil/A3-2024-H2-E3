package ar.edu.unlam.mobile.scaffolding.domain.services

import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import javax.inject.Inject

class GameService
    @Inject
    constructor() : GameUseCases {
        private val diceList = listOf(Dice.ONE, Dice.TWO, Dice.THREE, Dice.FOUR, Dice.FIVE, Dice.SIX)

        override fun getRandomDicePair(): Pair<Dice, Dice> {
            val firstDice = diceList.random()
            val secondDice = diceList.random()
            return Pair(firstDice, secondDice)
        }

        override fun getDiceThrowResult(dicePair: Pair<Dice, Dice>): Int = dicePair.first.value + dicePair.second.value
    }

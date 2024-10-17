package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.Dice

interface GameUseCases {
    fun getRandomDicePair(): Pair<Dice, Dice>

    fun getDiceThrowResult(dicePair: Pair<Dice, Dice>): Int
}

package ar.edu.unlam.mobile.scaffolding.domain.usecases

import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import kotlinx.coroutines.flow.Flow

interface GameUseCases {
    fun getRandomDice(useShake: Boolean): Flow<Dice>
}

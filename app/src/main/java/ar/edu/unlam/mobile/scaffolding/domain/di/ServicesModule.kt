package ar.edu.unlam.mobile.scaffolding.domain.di

import ar.edu.unlam.mobile.scaffolding.domain.services.GameService
import ar.edu.unlam.mobile.scaffolding.domain.services.PlayCardService
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import ar.edu.unlam.mobile.scaffolding.domain.usecases.PlayCardUseCases
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServicesModule {
    @Binds
    abstract fun bindGameService(gameService: GameService): GameUseCases

    @Binds
    abstract fun bindPlayCardService(playCardService: PlayCardService): PlayCardUseCases
}

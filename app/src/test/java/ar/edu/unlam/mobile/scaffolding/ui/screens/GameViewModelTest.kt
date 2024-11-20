package ar.edu.unlam.mobile.scaffolding.ui.screens

import ar.edu.unlam.mobile.scaffolding.domain.models.CardType
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import ar.edu.unlam.mobile.scaffolding.fakes.CaptureUserPhotoUseCasesFake
import ar.edu.unlam.mobile.scaffolding.fakes.GameUseCasesFake
import ar.edu.unlam.mobile.scaffolding.fakes.PlayCardUseCasesFake
import ar.edu.unlam.mobile.scaffolding.ui.screens.game.GameState
import ar.edu.unlam.mobile.scaffolding.ui.screens.game.GameViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rules.TestCoroutineRule

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: GameViewModel
    private lateinit var gameUseCasesFake: GameUseCasesFake
    private lateinit var playCardUseCasesFake: PlayCardUseCasesFake
    private lateinit var captureUserPhotoFake: CaptureUserPhotoUseCasesFake

    @Before
    fun setUp() {
        gameUseCasesFake = GameUseCasesFake()
        playCardUseCasesFake = PlayCardUseCasesFake()
        captureUserPhotoFake = CaptureUserPhotoUseCasesFake()
        viewModel = GameViewModel(gameUseCasesFake, playCardUseCasesFake, captureUserPhotoFake)
    }

    @Test
    fun `initial state should be default`() {
        assertEquals(GameState(), viewModel.state.value)
    }

    @Test
    fun `when startGame is called, maxRounds should be set`() {
        viewModel.startGame()
        assertEquals(3, viewModel.state.value.maxRounds)
    }

    @Test
    fun `when playerDrawCard is called, playerCard should be updated`() = runTest {
        val expectedCard = PlayCard(CardValue.ACE, CardType.SPADES)
        playCardUseCasesFake.setCardToDraw(expectedCard)

        viewModel.playerDrawCard()
        advanceUntilIdle()

        assertEquals(expectedCard, viewModel.state.value.playerCard)
    }

    @Test
    fun `when playerThrowDices is called, dice results should be updated`() = runTest {
        val dice = Dice.ONE
        gameUseCasesFake.emitDicePair(dice)

        viewModel.playerThrowDices()
        advanceUntilIdle()

        assertEquals(dice, viewModel.state.value.dice)
    }



    @Test
    fun `nextRound should increment currentRound`() = runTest {
        viewModel.startGame()
        val initialRound = viewModel.state.value.currentRound

        viewModel.nextRound()
        advanceUntilIdle()

        assertEquals(initialRound, viewModel.state.value.currentRound)
    }




}
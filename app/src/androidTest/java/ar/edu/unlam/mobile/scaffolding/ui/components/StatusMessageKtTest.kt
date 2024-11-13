package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import ar.edu.unlam.mobile.scaffolding.domain.models.CardValue
import ar.edu.unlam.mobile.scaffolding.domain.models.PlayCard
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class StatusMessageKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun statusMessage_displaysCorrectTextWhenIsUserTurn() {
        val isPlayerTurn = true
        val playerCard = PlayCard(CardValue.TEN)
        val statusMessage = "Tus dados deberan sumar ${playerCard.value.numericValue}"

        composeTestRule.setContent {
            StatusMessage(
                isPlayerTurn = isPlayerTurn,
                statusMesssage = statusMessage,
            )
        }

        composeTestRule.onNodeWithText("Turno de Jugador").assertIsDisplayed()
        composeTestRule.onNodeWithText(statusMessage).assertIsDisplayed()
    }

    @Test
    fun statusMessage_displaysCorrectTextWhenIsCpuTurn() {
        val isPlayerTurn = false
        val playerCard = PlayCard(CardValue.TEN)
        val statusMessage = "Tus dados deberan sumar ${playerCard.value.numericValue}"

        composeTestRule.setContent {
            StatusMessage(
                isPlayerTurn = isPlayerTurn,
                statusMesssage = statusMessage,
            )
        }

        composeTestRule.onNodeWithText("Turno de Cpu").assertIsDisplayed()
        composeTestRule.onNodeWithText(statusMessage).assertIsDisplayed()
    }

    @Test
    fun statusMessage_displaysCorrectValueWhenCardWasDrawn() {
        val isPlayerTurn = false
        val playerCard = PlayCard(CardValue.TEN)
        val statusMessage = "Tus dados deberan sumar ${playerCard.value.numericValue}"

        composeTestRule.setContent {
            StatusMessage(
                isPlayerTurn = isPlayerTurn,
                statusMesssage = statusMessage,
            )
        }

        composeTestRule.onNodeWithText("Turno de Cpu").assertIsDisplayed()
        composeTestRule.onNodeWithText(statusMessage).assertIsDisplayed()
    }
}

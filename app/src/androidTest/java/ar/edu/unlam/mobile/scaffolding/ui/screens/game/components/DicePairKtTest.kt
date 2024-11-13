package ar.edu.unlam.mobile.scaffolding.ui.screens.game.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class DicePairKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun dicePair_displaysTwoDice() {
        val diceOne = Dice.ONE
        val diceTwo = Dice.TWO

        composeTestRule.setContent {
            DicePair(
                diceOne = diceOne,
                diceTwo = diceTwo,
            )
        }

        composeTestRule.onNodeWithTag("Dice1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Dice2").assertIsDisplayed()
    }
}

package ar.edu.unlam.mobile.scaffolding.domain.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameServiceTest {
    private lateinit var context: Context
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var gameService: GameService

    @Before
    fun setUp() {
        context = mockk()
        sensorManager = mockk()
        accelerometer = mockk()
        every { context.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager
        every { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) } returns accelerometer
        gameService = GameService(context)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `getRandomDicePairForCPU returns a valid pair of dice`() =
        runTest {
            val dicePair = gameService.getRandomDicePairForCPU().first()
            advanceUntilIdle()
            assertNotNull(dicePair)
            assertTrue(dicePair.first in Dice.entries.toTypedArray())
            assertTrue(dicePair.second in Dice.entries.toTypedArray())
        }

    @Test
    fun `getDiceThrowResult returns the sum of dice values`() {
        val dicePair = Pair(Dice.THREE, Dice.FOUR)
        val result = gameService.getDiceThrowResult(dicePair)

        assertEquals(7, result)
    }
}

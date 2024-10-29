package ar.edu.unlam.mobile.scaffolding.domain.services

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import ar.edu.unlam.mobile.scaffolding.domain.models.Dice
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameUseCases
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.sqrt

class GameService
    @Inject
    constructor(
        @ApplicationContext context: Context,
    ) : GameUseCases {
        private val diceList = listOf(Dice.ONE, Dice.TWO, Dice.THREE, Dice.FOUR, Dice.FIVE, Dice.SIX)
        private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        private var shakeDetected = false
        private var currentAcceleration = 0f
        private var lastAcceleration = 0f
        private var acceleration = 0f
        private val targetAcceleration = 40f

        override fun getRandomDicePair(): Flow<Pair<Dice, Dice>> =
            flow {
                val firstDice = diceList.random()
                val secondDice = diceList.random()
                throwDices()
                while (shakeDetected.not()) {
                }
                shakeDetected = false
                emit(Pair(firstDice, secondDice))
            }

        override fun getDiceThrowResult(dicePair: Pair<Dice, Dice>): Int = dicePair.first.value + dicePair.second.value

        private fun throwDices() {
            sensorManager.registerListener(
                sensorEventListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_GAME,
            )
        }

        private val sensorEventListener =
            object : SensorEventListener {
                override fun onAccuracyChanged(
                    sensor: Sensor,
                    accuracy: Int,
                ) {
                }

                override fun onSensorChanged(event: SensorEvent) {
                    when (event.sensor.type) {
                        Sensor.TYPE_ACCELEROMETER -> {
                            val x = event.values[0]
                            val y = event.values[1]
                            val z = event.values[2]
                            lastAcceleration = currentAcceleration
                            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                            val delta: Float = currentAcceleration - lastAcceleration
                            acceleration = acceleration * 0.9f + delta
                            if (acceleration > targetAcceleration) {
                                sensorManager.unregisterListener(this)
                                shakeDetected = true
                            }
                        }
                    }
                }
            }
    }

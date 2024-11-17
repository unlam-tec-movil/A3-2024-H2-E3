package ar.edu.unlam.mobile.scaffolding.domain.models

enum class CardValue(
    val numericValue: Int,
) {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
}

fun CardValue.toNumberName(): String =
    when (this) {
        CardValue.ACE -> "ace"
        CardValue.TWO -> "2"
        CardValue.THREE -> "3"
        CardValue.FOUR -> "4"
        CardValue.FIVE -> "5"
        CardValue.SIX -> "6"
    }

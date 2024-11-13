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
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
}

fun CardValue.toNumberName(): String =
    when (this) {
        CardValue.ACE -> "ace"
        CardValue.TWO -> "2"
        CardValue.THREE -> "3"
        CardValue.FOUR -> "4"
        CardValue.FIVE -> "5"
        CardValue.SIX -> "6"
        CardValue.SEVEN -> "7"
        CardValue.EIGHT -> "8"
        CardValue.NINE -> "9"
        CardValue.TEN -> "10"
        CardValue.JACK -> "jack"
        CardValue.QUEEN -> "qween"
        CardValue.KING -> "king"
    }

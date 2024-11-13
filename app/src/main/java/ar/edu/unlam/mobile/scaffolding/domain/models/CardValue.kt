package ar.edu.unlam.mobile.scaffolding.domain.models

enum class CardValue {
    ACE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
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

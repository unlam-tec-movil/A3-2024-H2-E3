package ar.edu.unlam.mobile.scaffolding.domain.models

data class PlayCard(
    val value: CardValue = CardValue.ACE,
    val type: CardType = CardType.CLUBS,
) {
    override fun toString(): String = "${value.name} of ${type.name}"
}

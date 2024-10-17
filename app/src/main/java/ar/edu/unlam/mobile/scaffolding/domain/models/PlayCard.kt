package ar.edu.unlam.mobile.scaffolding.domain.models

data class PlayCard(
    val value: CardValue,
    val type: CardType,
) {
    override fun toString(): String = "${value.name} of ${type.name}"
}

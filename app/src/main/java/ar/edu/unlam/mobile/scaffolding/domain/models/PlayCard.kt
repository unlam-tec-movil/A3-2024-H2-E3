package ar.edu.unlam.mobile.scaffolding.domain.models

data class PlayCard(val value: CardValue, val type: CardType) {
    override fun toString(): String {
        return "${value.name} of ${type.name}"
    }
}
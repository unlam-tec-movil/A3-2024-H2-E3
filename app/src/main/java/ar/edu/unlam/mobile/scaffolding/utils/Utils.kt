package ar.edu.unlam.mobile.scaffolding.utils
fun <T> Boolean.then(block: () -> T): T? {
    return if (this) block() else null
}

fun <T> T?.otherwise(block: () -> T): T {
    return block()
}
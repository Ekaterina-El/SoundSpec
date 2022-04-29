package el.ka.soundspec.helpers

fun mapAmplitudeToLevel(
    value: Int,
    minFrom: Int,
    maxFrom: Int,
    minTo: Int,
    maxTo: Int
): Int {
    val ordinal = minFrom..maxFrom
    val target = minTo..maxTo

    val ratio = value.toFloat() / (ordinal.last - ordinal.first)
    return (ratio * (target.last - target.first)).toInt()
}
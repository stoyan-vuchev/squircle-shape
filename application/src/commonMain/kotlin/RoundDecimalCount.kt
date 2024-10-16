import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.roundDecimalCountTo(
    decimalCount: Int
): Float {
    val integerDigits = this.toInt()
    val floatDigits = ((this - integerDigits) * 10f.pow(decimalCount)).roundToInt()
    return "${integerDigits}.${floatDigits}".toFloat()
}
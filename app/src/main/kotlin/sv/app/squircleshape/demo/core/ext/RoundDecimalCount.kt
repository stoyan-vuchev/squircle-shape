package sv.app.squircleshape.demo.core.ext

import java.math.RoundingMode
import java.text.DecimalFormat

fun Float.roundDecimalCountTo(
    decimalCount: Int,
    roundingMode: RoundingMode = RoundingMode.FLOOR
): Float {
    val clampedDecimalCount = decimalCount.coerceAtLeast(1)
    val pattern = "#." + (1..clampedDecimalCount).map { "#" }.joinToString("") { it }
    val df = DecimalFormat(pattern).apply { this.roundingMode = roundingMode }
    return df.format(this).replace(",", ".").toFloat()
}
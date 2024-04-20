package processor

import java.text.DecimalFormat

enum class TransposeOption { MAIN_DIAGONAL, SIDE_DIAGONAL, VERTICAL_LINE, HORIZONTAL_LINE }

fun List<List<Double>>.rowSize() = this.size
fun List<List<Double>>.columnSize() = this[0].size

fun List<List<Double>>.print() {
    println("The result is:")
    val df = DecimalFormat("#.##")
    this.forEach { row -> println(row.joinToString(" ") { "%6s".format(df.format(it)) }) }
    println()
}

fun List<List<Double>>.add(other: List<List<Double>>): List<List<Double>> {
    if (this.rowSize() == other.rowSize() && this.columnSize() == other.columnSize()) {
        return this.mapIndexed { r, row -> row.mapIndexed { c, item -> item + other[r][c] } }
    } else {
        throw IllegalArgumentException("The operation cannot be performed.\n")
    }
}

fun List<List<Double>>.multiply(num: Double): List<List<Double>> {
    return this.map { row -> row.map { it * num } }
}

fun List<List<Double>>.multiply(other: List<List<Double>>): List<List<Double>> {
    if (this.columnSize() == other.rowSize()) {
        return List(this.rowSize()) { i ->
            List(other.columnSize()) { j ->
                this[i].mapIndexed { k, it -> it * other[k][j] }.sum()
            }
        }
    } else {
        throw IllegalArgumentException("The operation cannot be performed.\n")
    }
}

fun List<List<Double>>.transpose(option: TransposeOption): List<List<Double>> {
    val operation: (List<List<Double>>, Int, Int) -> Double = { m, i, j ->
        when (option) {
            TransposeOption.MAIN_DIAGONAL -> m[j][i]
            TransposeOption.SIDE_DIAGONAL -> m[m.rowSize() - j - 1][m.columnSize() - i - 1]
            TransposeOption.VERTICAL_LINE -> m[i][m.columnSize() - j - 1]
            TransposeOption.HORIZONTAL_LINE -> m[m.rowSize() - i - 1][j]
        }
    }
    return List(this.rowSize()) { i -> List(this.columnSize()) { j -> operation(this, i, j) } }
}

fun List<List<Double>>.determinant(): Double {
    var det = 0.0
    val n = this.rowSize()
    if (n == 2) {
        det = this[0][0] * this[1][1] - this[1][0] * this[0][1]
    } else {
        for (col in 0 until n) {
            val minor = this.getMinor(0, col)
            val sign = if (col % 2 == 0) 1 else -1
            det += sign * this[0][col] * minor.determinant()
        }
    }
    return det
}

fun List<List<Double>>.getMinor(rowToRemove: Int, colToRemove: Int): List<List<Double>> {
    val minor = MutableList(this.size) { r ->
        this[r].filterIndexed { c, _ -> c != colToRemove }
    }
    minor.removeAt(rowToRemove)
    return minor
}

fun List<List<Double>>.cofactor(): List<List<Double>> {
    return List(this.rowSize()) { r ->
        List(this.columnSize()) { c ->
            val sign = if ((r + c) % 2 == 0) 1 else -1
            this.getMinor(r, c).determinant() * sign
        }
    }
}

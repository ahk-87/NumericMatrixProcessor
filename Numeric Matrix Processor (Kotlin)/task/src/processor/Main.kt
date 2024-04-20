package processor

import java.text.DecimalFormat

fun getMatrix(matrixPosition: String = ""): List<List<Double>> {
    print("Enter size of$matrixPosition matrix: ")
    val (r, c) = readln().split(" ").map(String::toInt)
    println("Enter$matrixPosition matrix:")
    return List(r) { readln().split(" ").map(String::toDouble) }
}

fun addMatrices() {
    val matrix1 = getMatrix(" first")
    val matrix2 = getMatrix(" second")
    try {
        matrix1.add(matrix2).print()
    } catch (e: Exception) {
        println(e.message)
    }
}

fun multiplyMatrixByConstant() {
    val matrix = getMatrix()
    print("Enter constant: ")
    val constant = readln().toDouble()

    matrix.multiply(constant).print()
}

fun multiplyMatrices() {
    val matrix1 = getMatrix(" first")
    val matrix2 = getMatrix(" second")
    try {
        matrix1.multiply(matrix2).print()
    } catch (e: Exception) {
        println(e.message)
    }
}

fun findTranspose() {
    print(
        """
            1. Main diagonal
            2. Side diagonal
            3. Vertical line
            4. Horizontal line
            Your choice: """.trimIndent()
    )
    val choice = readln().toInt()
    val matrix = getMatrix()
    matrix.transpose(TransposeOption.values()[choice - 1]).print()
}

fun findDeterminant() {
    val matrix = getMatrix()
    println("The result is:")
    val df = DecimalFormat("#.##")
    println(df.format(matrix.determinant()))
    println()
}

fun findInverse() {
    val matrix = getMatrix()
    val det = matrix.determinant()
    if (det == 0.0) {
        println("This matrix doesn't have an inverse.")
        return
    }
    val adj = matrix.cofactor().transpose(TransposeOption.MAIN_DIAGONAL)
    adj.multiply(1 / det).print()
}

fun main() {
    while (true) {
        println(
            """
               1. Add matrices
               2. Multiply matrix by a constant
               3. Multiply matrices
               4. Transpose matrix
               5. Calculate a determinant
               6. Inverse matrix
               0. Exit""".trimIndent()
        )
        when (readln().toInt()) {
            1 -> addMatrices()
            2 -> multiplyMatrixByConstant()
            3 -> multiplyMatrices()
            4 -> findTranspose()
            5 -> findDeterminant()
            6 -> findInverse()
            0 -> break
        }
    }
}

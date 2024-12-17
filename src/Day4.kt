import kotlin.io.path.Path
import kotlin.io.path.readLines

fun Pair<Int, Int>.toWord(data: List<CharArray>, iInc: Int, jInc: Int, length: Int = 4): String =
    (0..<length).map { i -> data[this.first + iInc * i][this.second + jInc * i] }.joinToString("")

fun main() {
    val data = Path("resources/day-4.input.txt").readLines().map { it.toCharArray() }
    println(xmas(data).filter { it == "XMAS" || it == "SAMX" }.size)
    println(xMas(data).filter { (a, b) -> (a == "MAS" || a == "SAM") && (b == "MAS" || b == "SAM") }.size)
}

private fun xmas(data: List<CharArray>): MutableList<String> {
    val l = mutableListOf<String>()
    for (i in data.indices) {
        for (j in data[i].indices) {
            if (data[i][j] != 'X' && data[i][j] != 'S') continue
            //Horizontal
            if (j < data[i].size - 3) l.add(Pair(i, j).toWord(data, 0, 1))

            //Vertical
            if (i < data.size - 3) l.add(Pair(i, j).toWord(data, 1, 0))

            //Diagonal
            //Right Down Diag
            if (j < data[i].size - 3 && i < data.size - 3) l.add(Pair(i, j).toWord(data, 1, 1))
            //Left Down Diag
            if (j >= 3 && i < data.size - 3) l.add(Pair(i, j).toWord(data, 1, -1))
        }
    }
    return l
}

private fun xMas(data: List<CharArray>): MutableList<Pair<String, String>> {
    val l = mutableListOf<Pair<String, String>>()
    for (i in 1..<(data.size - 1)) {
        for (j in 1..<(data[i].size - 1)) {
            if (data[i][j] != 'A') continue
            l.add(
                Pair(
                    //Diagonal \
                    Pair(i - 1, j - 1).toWord(data, 1, 1, 3),
                    //Diagonal /
                    Pair(i - 1, j + 1).toWord(data, 1, -1, 3)
                )
            )
        }
    }
    return l
}
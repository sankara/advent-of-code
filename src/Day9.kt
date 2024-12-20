import kotlin.io.path.Path
import kotlin.io.path.readText

class Day9(s: String) {
    private val fs = run {
        parseInput(s)
    }

    private fun parseInput(s: String): IntArray {
        var size = 0
        s.forEach { c ->
            size += (c - '0')
        }
        val arr = IntArray(size) { -1 }

        var blockIndex = 0
        s.forEachIndexed { i, c ->
            val blockSize = c - '0'
            if (i % 2 == 0) (blockIndex..<blockIndex + blockSize).forEach { arr[it] = i / 2 }
            blockIndex += blockSize
        }
        return arr
    }

    fun optimise() {
        var i = 0
        var j = fs.size - 1
        while (i < j) {
            while (i < fs.size && fs[i] != -1) i++
            while (j > 0 && fs[j] == -1) j--
            if (i < j && fs[j] != -1) {
                fs[i] = fs[j]
                fs[j] = -1
            }
        }
    }

    fun optimiseContiguous() {
        //j keeps track of the file we're moving. It should strictly move leftwards. That's the breaking condition
        //i starts afresh for every file.
        var j = fs.size - 1
        while (j >= 0) {
            var i = 0
            val fileSize = countBlockSize(j, -1)
            while (i < fs.size && fs[j] != -1 && i < j) {
                val blockSize = countBlockSize(i, 1)
                if (fs[i] == -1 && blockSize >= fileSize) {
                    copyFile(i, j, fileSize)
                    break
                } else {
                    i += blockSize
                }
            }
            j -= fileSize
        }
    }

    private fun copyFile(i: Int, j: Int, fileSize: Int) {
        for (k in j downTo j - fileSize + 1) {
            fs[i + (j - k)] = fs[k]
            fs[k] = -1
        }
    }

    private fun countBlockSize(blockPos: Int, dir: Int): Int {
        var blockSize = 0
        var k = blockPos
        while (k in fs.indices && fs[k] == fs[blockPos]) {
            blockSize++
            k += dir
        }
        return blockSize
    }

    fun checksum(): Long {
        var sum = 0L
        for (i in fs.indices)
            if (fs[i] >= 0) sum += fs[i] * i
        return sum
    }

    override fun toString(): String {
        return fs.joinToString("") { if (it >= 0) it.toString() else "." }
    }
}

fun main() {
    //Day9("2333133121414131402").run()
    //1928
    val day9P1 = Day9(Path("resources/day-9.input.txt").readText())
    println(day9P1)
    day9P1.optimise()
    println(day9P1)
    println(day9P1.checksum())
    //6607511583593

    val day9P2 = Day9(Path("resources/day-9.input.txt").readText())
    day9P2.optimiseContiguous()
    println(day9P2)
    println(day9P2.checksum())
    //6636608781232
}
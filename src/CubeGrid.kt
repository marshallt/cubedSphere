import kotlin.math.abs
import kotlin.math.max

class CubeGrid(val size: Int) {
    private val faceSize = size * size
    private val cells = Array(faceSize * 6, {Cell(0)})
    val maxXY = size - 1

    fun index(face: Int, x: Int, y: Int): Int {
        return  (face * faceSize) + (y * size) + x
    }

    fun location(index: Int): CubeVec {
        var i = index
        val face = i / faceSize
        i = i.rem(face * faceSize)
        val y = i / size
        i = i.rem(y * size)
        return CubeVec(this, face, i, y) //i is the remainder which is the X value
    }

    fun cells(vec: CubeVec): Cell {
        val index = (vec.face * faceSize) + (vec.y * size) + vec.x
        return cells[index]
    }

    fun cells(face: Int, x: Int, y: Int): Cell {
        if (face > 5 || face < 0) throw Exception("CubeGrid.cells(): Face must be between 0 and 5. $face: ($x, $y)")
        if (x < 0 || x > size - 1) throw Exception("CubeGrid.cells(): x must be between 0 and ${size - 1}. $face: ($x, $y)")
        if (y < 0 || y > size - 1) throw Exception("CubeGrid.cells(): y must be between 0 and ${size - 1}. $face: ($x, $y)")

        val index = (face * faceSize) + (y * size) + x
        return cells[index]
    }

    fun reset() {
        cells.forEach({
            it.elevation = 0
            it.climate = 0
            it.isEmpty = false
        })
    }


}

class GridRef(val grid: Grid, val face: Int, val x: Int, val y: Int) {

    init {
        if (face > 5 || face < 0) throw Exception("GridRef constructor: Face must be between 0 and 5. $face: ($x, $y)")
        if (x < 0 || x > grid.size - 1) throw Exception("GridRef constructor: x must be between 0 and ${grid.size - 1}. $face: ($x, $y)")
        if (y < 0 || y > grid.size - 1) throw Exception("GridRef constructor: y must be between 0 and ${grid.size - 1}. $face: ($x, $y)")

    }

    operator fun component1(): Int {
        return face
    }

    operator fun component2(): Int {
        return x
    }

    operator fun component3(): Int {
        return y
    }

    override fun toString(): String {
        return "Face $face: ($x, $y)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is GridRef) {
            return (face == other.face && x == other.x && y == other.y)
        } else {
            return false
        }
    }
}
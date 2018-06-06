class GridRef(val face: Int, val x: Int, val y: Int) {
    fun add(dx: Int, dy: Int): GridRef {
        return GridRef(face, x + dx, y + dy)
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
}
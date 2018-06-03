class Grid(val size: Int) {
    val cells = Array<Cell>(size * size * 6, {Cell(0)})

    fun cells(face: Int, x: Int, y: Int): Cell {
        if (face > 5 || face < 0) throw Exception("Face must be between 0 and 5")
        if (x < 0 || x > size - 1) throw Exception("x must be between 0 and ${size - 1}")
        if (y < 0 || y > size - 1) throw Exception("y must be between 0 and ${size - 1}")

        val index = (face * size * size) + (y * size) + x
        return cells[index]
    }

    fun neighbors(face: Int, x: Int, y: Int, emptyOnly: Boolean = false): ArrayList<Cell> {
        val result = ArrayList<Cell>()
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dy == 0 && dx == 0) break
                if (emptyOnly) {
                    if (cells(face, x + dx, y + dy).isEmpty) {
                        result.add(cells(face, x + dx, y + dy))
                    }
                } else {
                        result.add(cells(face, x + dx, y + dy))
                }
            }
        }
        return result
    }
}
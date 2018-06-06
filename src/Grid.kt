class Grid(val size: Int) {
    val faceSize = size * size
    val cells = Array<Cell>(faceSize * 6, {Cell(0)})

    fun index(face: Int, x: Int, y: Int): Int {
        return  (face * faceSize) + (y * size) + x
    }

    fun location(index: Int): GridRef {
        var i = index
        val face = i / faceSize
        i = i.rem(face * faceSize)
        val y = i / size
        i = i.rem(y * size)
        return GridRef(face, i, y) //i is the remainder which is the X value
    }

    fun cells(gridRef: GridRef): Cell {
        return cells(gridRef.face, gridRef.x, gridRef.y)
    }

    fun cells(face: Int, x: Int, y: Int): Cell {
        if (face > 5 || face < 0) throw Exception("Face must be between 0 and 5")
        if (x < 0 || x > size - 1) throw Exception("x must be between 0 and ${size - 1}")
        if (y < 0 || y > size - 1) throw Exception("y must be between 0 and ${size - 1}")

        val index = (face * size * size) + (y * size) + x
        return cells[index]
    }

    fun reset() {
        cells.forEach({
            it.elevation = 0
            it.climate = 0
            it.isEmpty = false
        })
    }

    fun neighborRefs(face: Int, x: Int, y: Int, emptyOnly: Boolean = false): ArrayList<GridRef> {
        val result = ArrayList<GridRef>()
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dy == 0 && dx == 0) {} //skip the cell itself
                else {
                    if (emptyOnly) {
                        if (cells(face, x + dx, y + dy).isEmpty) {
                            result.add(GridRef(face, x + dx, y + dy))
                        }
                    } else {
                        result.add(GridRef(face, x + dx, y + dy))
                    }
                }
            }
        }
        return result
    }

    fun neighborCells(face: Int, x: Int, y: Int, emptyOnly: Boolean = false): ArrayList<Cell> {
        val refs = neighborRefs(face, x, y, emptyOnly)
        val result = ArrayList<Cell>()
        refs.forEach{result.add(cells(it))}
        return result
    }

    fun neighborCells(gridRef: GridRef, emptyOnly: Boolean = false): ArrayList<Cell> {
        return neighborCells(gridRef.face, gridRef.x, gridRef.y, emptyOnly)
    }

}


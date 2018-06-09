import kotlin.math.abs
import kotlin.math.max

class Grid(val size: Int) {
    private val faceSize = size * size
    private val cells = Array(faceSize * 6, {Cell(0)})
    val maxXY = size - 1

    fun index(face: Int, x: Int, y: Int): Int {
        return  (face * faceSize) + (y * size) + x
    }

    fun location(index: Int): GridRef {
        var i = index
        val face = i / faceSize
        i = i.rem(face * faceSize)
        val y = i / size
        i = i.rem(y * size)
        return GridRef(this, face, i, y) //i is the remainder which is the X value
    }

    fun cells(ref: GridRef): Cell {
        val index = (ref.face * faceSize) + (ref.y * size) + ref.x
        return cells[index]
    }

    fun cells(face: Int, x: Int, y: Int): Cell {
        if (face > 5 || face < 0) throw Exception("Grid.cells(): Face must be between 0 and 5. $face: ($x, $y)")
        if (x < 0 || x > size - 1) throw Exception("Grid.cells(): x must be between 0 and ${size - 1}. $face: ($x, $y)")
        if (y < 0 || y > size - 1) throw Exception("Grid.cells(): y must be between 0 and ${size - 1}. $face: ($x, $y)")

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

    fun neighborRef(ref: GridRef, dx: Int, dy: Int): GridRef {
        println("neighborRef($ref), dx = $dx, dy = $dy")
        if (dx < -1 || dx > 1) throw Exception("Grid.neighbor(): x must be in {-1, 0, 1} ($dx, $dy)")
        if (dy < -1 || dy > 1) throw Exception("Grid.neighbor(): y must be in {-1, 0, 1} ($dx, $dy)")
        var newX = ref.x + dx
        var newY = ref.y + dy

        if (newX in 0 until size && newY in 0 until size) {//no Overflow
            return GridRef(this, ref.face, newX, newY)
        }

        val underflowX = newX < 0
        val overflowX = newX > maxXY
        val underflowY = newY < 0
        val overflowY = newY > maxXY
        val underflowBoth = underflowX && underflowY
        val overflowBoth = overflowX && overflowY

        println("     overflow. ${ref.face}, $newX, $newY")
        when (ref.face) {
            0 -> {
                if (underflowBoth) return GridRef(this, 3, maxXY, maxXY)
                if (overflowBoth) return GridRef(this, 1, 0, maxXY)
                if (overflowX && underflowY) return GridRef(this, 4, 0, maxXY)
                if (underflowX && overflowY) return GridRef(this, 3, maxXY, maxXY)
                if (underflowX) return GridRef(this, 3, maxXY, newY)
                if (underflowY) return GridRef(this, 4, 0, newX)
                if (overflowX) return GridRef(this, 1, 0, newY)
                if (overflowY) return GridRef(this, 5, 0, maxXY - newX)
            }
            1 -> {
                if (underflowBoth) return GridRef(this, 0, maxXY, 0)
                if (overflowBoth) return GridRef(this, 2, 0, maxXY)
                if (overflowX && underflowY) return GridRef(this, 2, 0, 0)
                if (underflowX && overflowY) return GridRef(this, 0, maxXY, maxXY)
                if (underflowX) return GridRef(this, 0, maxXY, newY)
                if (underflowY) return GridRef(this, 4, newX, maxXY)
                if (overflowX) return GridRef(this, 2, 0, newY)
                if (overflowY) return GridRef(this, 5, newX, 0)
            }
            2 -> {
                if (underflowBoth) return GridRef(this, 1, maxXY, 0)
                if (overflowBoth) return GridRef(this, 3, 0, maxXY)
                if (overflowX && underflowY) return GridRef(this, 3, 0, 0)
                if (underflowX && overflowY) return GridRef(this, 5, maxXY, 0)
                if (underflowX) return GridRef(this, 1, maxXY, newY)
                if (underflowY) return GridRef(this, 4, maxXY, maxXY - newX)
                if (overflowX) return GridRef(this, 3, 0, newY)
                if (overflowY) return GridRef(this, 5, maxXY, newX)
            }
            3 -> {
                if (underflowBoth) return GridRef(this, 2, maxXY, 0)
                if (overflowBoth) return GridRef(this, 0, 0, maxXY)
                if (overflowX && underflowY) return GridRef(this, 0, 0, 0)
                if (underflowX && overflowY) return GridRef(this, 2, maxXY, maxXY)//
                if (underflowX) return GridRef(this, 2, maxXY, newY)
                if (underflowY) return GridRef(this, 4, maxXY - newX, 0)
                if (overflowX) return GridRef(this, 0, 0, newY)
                if (overflowY) return GridRef(this, 5, maxXY - newX, maxXY)
            }
            4 -> {
                if (underflowBoth) return GridRef(this, 0, 0, 0)
                if (overflowBoth) return GridRef(this, 1, maxXY, 0)
                if (overflowX && underflowY) return GridRef(this, 2, maxXY, 0)
                if (underflowX && overflowY) return GridRef(this, 1, 0, 0)//
                if (underflowX) return GridRef(this, 0, newY, 0)
                if (underflowY) return GridRef(this, 3, maxXY - newX, 0)
                if (overflowX) return GridRef(this, 2, maxXY - newY, 0)
                if (overflowY) return GridRef(this, 1, newX, 0)
            }
            5 -> {
                if (underflowBoth) return GridRef(this, 0, maxXY, maxXY)
                if (overflowBoth) return GridRef(this, 3, 0, maxXY)
                if (overflowX && underflowY) return GridRef(this, 2, 0, maxXY)
                if (underflowX && overflowY) return GridRef(this, 3, maxXY, 0)//
                if (underflowX) return GridRef(this, 0, maxXY - newY, maxXY)
                if (underflowY) return GridRef(this, 1, newX, maxXY)
                if (overflowX) return GridRef(this, 2, newY, maxXY)
                if (overflowY) return GridRef(this, 3, maxXY - newX, maxXY)
            }
        }
        throw Exception("could not calculate neighbor")
    }

    fun neighborRefs(ref: GridRef, emptyOnly: Boolean = false): ArrayList<GridRef> {
        val result = ArrayList<GridRef>()
        var tmpRef: GridRef
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dy == 0 && dx == 0) {} //skip the cell itself
                else {
                    if (emptyOnly) {
                        if (cells(ref).isEmpty) {
                            result.add(neighborRef(ref, dx, dy))
                        }
                    } else {
                        tmpRef = neighborRef(ref, dx, dy)
                        println("     result: $tmpRef")
                        result.add(tmpRef)
                    }
                }
            }
        }
        return result
    }

    fun neighborCells(face: Int, x: Int, y: Int, emptyOnly: Boolean = false): ArrayList<Cell> {
        return this.neighborCells(GridRef(this, face, x, y), emptyOnly)
    }

    fun neighborCells(ref: GridRef, emptyOnly: Boolean = false): ArrayList<Cell> {
        val refs = this.neighborRefs(ref, emptyOnly)
        val result = ArrayList<Cell>()
        refs.forEach{result.add(cells(it))}
        return result
    }

    fun offset(ref: GridRef, offsetRef: GridRef): GridRef {
        if (ref.face != offsetRef.face) throw Exception("can't offset if the two points don't start on the same face")

        val dx = offsetRef.x
        val dy = offsetRef.y
        val iterations = max(abs(dx), abs(dy))
        var offsetPerIter = GridRefDouble(this, ref.face, dx.toDouble() / iterations, dy.toDouble() / iterations)

        var x = 0
        var y = 0
        var movedX = 0
        var movedY = 0
        var neighbor = ref
        var previousFace: Int
        var temp = 0.0

        for (i in 1..iterations) {
            x = ((i * offsetPerIter.x) - movedX).toInt()
            y = ((i * offsetPerIter.y) - movedY).toInt()
            //todo: i think this needs to be adjusted if offset is adjusted for face change
            movedX += x
            movedY += y
            previousFace = neighbor.face
            neighbor = neighborRef(neighbor, x, y)

            //if you change faces, you may need to adjust the offset vector
            if (neighbor.face != previousFace) {
                offsetPerIter = adjustDoubleOffsetForFaceChange(offsetPerIter, previousFace)
            }
        }
        return neighbor
    }

    fun adjustOffsetForFaceChange(offset: GridRef, newFace: Int): GridRef{
        val x = offset.x
        val y = offset.y
        var newX: Int = 0
        var newY: Int = 0
        var temp: Int = 0
        when (offset.face) {
            0 -> {
                if (newFace == 4) {
                    temp = x
                    newX = -y
                    newY = temp
                }
                if (newFace == 5) {
                    temp = x
                    newX = y
                    newY = -temp
                }
            }
            2 -> {
                if (newFace == 4) {
                    temp = x
                    newX = y
                    newY = -temp
                }
                if (newFace == 5) {
                    temp = x
                    newX = -y
                    newY = -temp
                }
            }
            3 -> {
                if (newFace == 4) {
                    newX = -x
                    newY = -y
                }
                if (newFace == 5) {
                    newX = -x
                    newY = -y
                }
            }
        }
        return GridRef(this, newFace, newX, newY)
    }

    fun adjustDoubleOffsetForFaceChange(offset: GridRefDouble, newFace: Int): GridRefDouble{
        val x = offset.x
        val y = offset.y
        var newX = 0.0
        var newY = 0.0
        var temp = 0.0
        when (offset.face) {
            0 -> {
                if (newFace == 4) {
                    temp = x
                    newX = -y
                    newY = temp
                }
                if (newFace == 5) {
                    temp = x
                    newX = y
                    newY = -temp
                }
            }
            2 -> {
                if (newFace == 4) {
                    temp = x
                    newX = y
                    newY = -temp
                }
                if (newFace == 5) {
                    temp = x
                    newX = -y
                    newY = -temp
                }
            }
            3 -> {
                if (newFace == 4) {
                    newX = -x
                    newY = -y
                }
                if (newFace == 5) {
                    newX = -x
                    newY = -y
                }
            }
        }
        return GridRefDouble(this, newFace, newX, newY)
    }

}

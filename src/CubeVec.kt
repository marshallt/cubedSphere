import kotlin.math.abs
import kotlin.math.max

class CubeVec(val cubeGrid: CubeGrid, val face: Int, val x: Int, val y: Int) {

    init {
        if (face > 5 || face < 0) throw Exception("CubeVec constructor: Face must be between 0 and 5. $face: ($x, $y)")
        if (x < 0 || x > cubeGrid.size - 1) throw Exception("CubeVec constructor: x must be between 0 and ${cubeGrid.size - 1}. $face: ($x, $y)")
        if (y < 0 || y > cubeGrid.size - 1) throw Exception("CubeVec constructor: y must be between 0 and ${cubeGrid.size - 1}. $face: ($x, $y)")

    }

    //******************************************************************************************************************
    fun neighborVec(dx: Int, dy: Int): CubeVec {
        val maxXY = cubeGrid.size - 1
        if (dx < -1 || dx > 1) throw Exception("CubeGrid.neighbor(): x must be in {-1, 0, 1} $dx, $dy")
        if (dy < -1 || dy > 1) throw Exception("CubeGrid.neighbor(): y must be in {-1, 0, 1} $dx, $dy")

        var newX = this.x + dx
        var newY = this.y + dy

        if (newX in 0..maxXY && newY in 0..maxXY) {//no Overflow
            return CubeVec(cubeGrid, this.face, newX, newY)
        }

        val underflowX = newX < 0
        val overflowX = newX > maxXY
        val underflowY = newY < 0
        val overflowY = newY > maxXY
        val underflowBoth = underflowX && underflowY
        val overflowBoth = overflowX && overflowY

        //println("     overflow. ${this.face}, $newX, $newY")
        when (this.face) {
            0 -> {
                if (underflowBoth) return CubeVec(cubeGrid, 3, maxXY, 0)
                if (overflowBoth) return CubeVec(cubeGrid, 1, 0, maxXY)
                if (overflowX && underflowY) return CubeVec(cubeGrid, 1, 0, 0)
                if (underflowX && overflowY) return CubeVec(cubeGrid, 3, maxXY, maxXY)
                if (underflowX) return CubeVec(cubeGrid, 3, maxXY, newY)
                if (underflowY) return CubeVec(cubeGrid, 4, 0, newX)
                if (overflowX) return CubeVec(cubeGrid, 1, 0, newY)
                if (overflowY) return CubeVec(cubeGrid, 5, 0, maxXY - newX)
            }
            1 -> {
                if (underflowBoth) return CubeVec(cubeGrid, 0, maxXY, 0)
                if (overflowBoth) return CubeVec(cubeGrid, 2, 0, maxXY)
                if (overflowX && underflowY) return CubeVec(cubeGrid, 2, 0, 0)
                if (underflowX && overflowY) return CubeVec(cubeGrid, 0, maxXY, maxXY)
                if (underflowX) return CubeVec(cubeGrid, 0, maxXY, newY)
                if (underflowY) return CubeVec(cubeGrid, 4, newX, maxXY)
                if (overflowX) return CubeVec(cubeGrid, 2, 0, newY)
                if (overflowY) return CubeVec(cubeGrid, 5, newX, 0)
            }
            2 -> {
                if (underflowBoth) return CubeVec(cubeGrid, 1, maxXY, 0)
                if (overflowBoth) return CubeVec(cubeGrid, 3, 0, maxXY)
                if (overflowX && underflowY) return CubeVec(cubeGrid, 3, 0, 0)
                if (underflowX && overflowY) return CubeVec(cubeGrid, 1, maxXY, maxXY)
                if (underflowX) return CubeVec(cubeGrid, 1, maxXY, newY)
                if (underflowY) return CubeVec(cubeGrid, 4, maxXY, maxXY - newX)
                if (overflowX) return CubeVec(cubeGrid, 3, 0, newY)
                if (overflowY) return CubeVec(cubeGrid, 5, maxXY, newX)
            }
            3 -> {
                if (underflowBoth) return CubeVec(cubeGrid, 2, maxXY, 0)
                if (overflowBoth) return CubeVec(cubeGrid, 0, 0, maxXY)
                if (overflowX && underflowY) return CubeVec(cubeGrid, 0, 0, 0)
                if (underflowX && overflowY) return CubeVec(cubeGrid, 2, maxXY, maxXY)//
                if (underflowX) return CubeVec(cubeGrid, 2, maxXY, newY)
                if (underflowY) return CubeVec(cubeGrid, 4, maxXY - newX, 0)
                if (overflowX) return CubeVec(cubeGrid, 0, 0, newY)
                if (overflowY) return CubeVec(cubeGrid, 5, maxXY - newX, maxXY)
            }
            4 -> {
                if (underflowBoth) return CubeVec(cubeGrid, 0, 0, 0)
                if (overflowBoth) return CubeVec(cubeGrid, 2, 0, 0)
                if (overflowX && underflowY) return CubeVec(cubeGrid, 2, maxXY, 0)
                if (underflowX && overflowY) return CubeVec(cubeGrid, 0, maxXY, 0)//
                if (underflowX) return CubeVec(cubeGrid, 0, newY, 0)
                if (underflowY) return CubeVec(cubeGrid, 3, maxXY - newX, 0)
                if (overflowX) return CubeVec(cubeGrid, 2, maxXY - newY, 0)
                if (overflowY) return CubeVec(cubeGrid, 1, newX, 0)
            }
            5 -> {
                if (underflowBoth) return CubeVec(cubeGrid, 0, maxXY, maxXY)
                if (overflowBoth) return CubeVec(cubeGrid, 2, maxXY, maxXY)
                if (overflowX && underflowY) return CubeVec(cubeGrid, 2, 0, maxXY)
                if (underflowX && overflowY) return CubeVec(cubeGrid, 0, 0, maxXY)//
                if (underflowX) return CubeVec(cubeGrid, 0, maxXY - newY, maxXY)
                if (underflowY) return CubeVec(cubeGrid, 1, newX, maxXY)
                if (overflowX) return CubeVec(cubeGrid, 2, newY, maxXY)
                if (overflowY) return CubeVec(cubeGrid, 3, maxXY - newX, maxXY)
            }
        }
        throw Exception("could not calculate neighbor")
    }

    //******************************************************************************************************************
    fun neighborVecs(): ArrayList<CubeVec> {
        val result = ArrayList<CubeVec>()
        var tmpVec: CubeVec
        for (dy in -1..1) {
            for (dx in -1..1) {
                if (dy == 0 && dx == 0) {} //skip the cell itself
                else {
                    tmpVec = this.neighborVec(dx, dy)
                    result.add(tmpVec)
                }
            }
        }
        return result
    }


    //******************************************************************************************************************
    fun add(offsetVec: CubeVec): CubeVec {
        if (this.face != offsetVec.face) throw Exception("Can't add CubeVecs that aren't on the same face. $this + $offsetVec")

        val dx = offsetVec.x
        val dy = offsetVec.y
        val iterations = max(abs(dx), abs(dy))
        var offsetPerIter = CubeVecDouble(cubeGrid, this.face, dx.toDouble() / iterations, dy.toDouble() / iterations)

        var x = 0
        var y = 0
        var movedX = 0
        var movedY = 0
        var neighbor = offsetVec
        var previousFace: Int

        for (i in 1..iterations) {
            x = ((i * offsetPerIter.x) - movedX).toInt()
            y = ((i * offsetPerIter.y) - movedY).toInt()
            //todo: i think "moved" needs to be adjusted if offset is adjusted for face change
            movedX += x
            movedY += y
            previousFace = neighbor.face
            neighbor = neighborVec(x, y)

            //if you change faces, you may need to adjust the offset vector
            if (neighbor.face != previousFace) {
                offsetPerIter = offsetPerIter.adjustForFaceChange(newFace = neighbor.face)
            }
        }
        return neighbor
    }

    fun adjustForFaceChange(newFace: Int): CubeVec{
        val x = this.x
        val y = this.y
        var newX: Int = 0
        var newY: Int = 0
        var temp: Int = 0
        when (this.face) {
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
        return CubeVec(cubeGrid, newFace, newX, newY)
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
        return "$face/($x, $y)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is CubeVec) {
            return (face == other.face && x == other.x && y == other.y)
        } else {
            return false
        }
    }
}
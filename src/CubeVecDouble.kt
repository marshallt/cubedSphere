class CubeVecDouble(val cubeGrid: CubeGrid, val face: Int, val x: Double, val y: Double) {

    fun moveToNewFace(newFace: Int): CubeVecDouble {
        var newX = 0.0
        var newY = 0.0
        var temp = 0.0
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
        return CubeVecDouble(cubeGrid, newFace, newX, newY)
    }

    fun adjustForFaceChange(newFace: Int): CubeVecDouble{
        val x = this.x
        val y = this.y
        var newX = 0.0
        var newY = 0.0
        var temp = 0.0

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
        return CubeVecDouble(cubeGrid, newFace, newX, newY)
    }


    fun toGridRef(): CubeVec {
        return CubeVec(cubeGrid, face, x.toInt(), y.toInt())
    }

    operator fun component1(): Int {
        return face
    }

    operator fun component2(): Double {
        return x
    }

    operator fun component3(): Double {
        return y
    }

    override fun toString(): String {
        return "Face $face: ($x, $y)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is CubeVecDouble) {
            return (face == other.face && x == other.x && y == other.y)
        } else {
            return false
        }
    }
}
object _test {
    fun neighbors(cubeGrid: CubeGrid) {
        var ref: CubeVec

        for (face in 0..5) {
            println("FACE $face")
            ref = CubeVec(cubeGrid, face, 0, 0)
            println(ref.neighborVecs())

            ref = CubeVec(cubeGrid, face, cubeGrid.maxXY, 0)
            println(ref.neighborVecs())

            ref = CubeVec(cubeGrid, face, 0, cubeGrid.maxXY)
            println(ref.neighborVecs())

            ref = CubeVec(cubeGrid, face, cubeGrid.maxXY, cubeGrid.maxXY)
            println(ref.neighborVecs())
        }
    }
}
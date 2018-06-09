object _test {
    fun offset(grid: Grid) {
        var ref = GridRef(grid, 1, 128, 128)
        println(grid.offset(ref, GridRef(grid,1, 3, 2)))

        ref = GridRef(grid,0, 255,128)
        println(grid.offset(ref, GridRef(grid,0, 1, 5)))
        println(grid.offset(ref, GridRef(grid,0, -5, 3)))



    }
}
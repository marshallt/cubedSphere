class CubeSphereGrid(val size: Int) {
    var cells = Array(6, { Array(size, {Array(size,{Int})})})  //array pointing to a cell in a list of all cells in the World


    init {
    }
}

data class Location(val face: Int, val x: Int, val y: Int)
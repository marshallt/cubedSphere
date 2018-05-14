class Plate(val world: World, var width: Int, var height: Int) {
    val EMPTY_CELL: Int = -1
    var location = Location(0,0,0)
    var heading = 0.0
    var speed = 0.0
    var rotation = 0.0
    var cells = Array(6, { Array(world.size, {Array(world.size,{EMPTY_CELL})})}) //cell pointer -1 means No Cell or Empty Cell


    fun generate(width: Int, height: Int) {

    }
}
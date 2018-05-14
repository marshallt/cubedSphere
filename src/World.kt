import java.util.*

class World(val size: Int) {
    var grid = CubeSphereGrid(size)

    init {
        val random = Random()
        //initialize with Perlin noise
        var variable = 0.0
        for (face in 0..5) {
            variable = random.nextInt(100).toDouble()
            for (x in 0..size - 1) {
                for (y in 0..size - 1) {
                    grid.cells[face][x][y].elevation = (ImprovedNoise.noise(x.toDouble()/variable, y.toDouble()/variable, 0.0) * 1000).toInt()
                }
            }
        }
    }
}